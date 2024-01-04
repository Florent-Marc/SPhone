package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.client.PacketStopSound;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketCallRequest implements IMessage {

    private boolean accept;
    private String targetName;
    private boolean response;

    public PacketCallRequest() {
    }

    public PacketCallRequest(boolean accept, String targetName) {
        this.accept = accept;
        this.targetName = targetName;
        this.response = false;
    }

    public PacketCallRequest(boolean accept, String targetName, boolean response) {
        this.accept = accept;
        this.targetName = targetName;
        this.response = response;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = buf.readBoolean();
        this.targetName = ByteBufUtils.readUTF8String(buf);
        this.response = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.accept);
        ByteBufUtils.writeUTF8String(buf, this.targetName);
        buf.writeBoolean(this.response);
    }

    public static class ServerHandler implements IMessageHandler<PacketCallRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCallRequest message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            EntityPlayer caller = player.world.getPlayerEntityByName(message.targetName);
            if (message.accept) {

                String callNumber = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(player));
                if (caller != null && callNumber != null) {
                    MinecraftForge.EVENT_BUS.post(new CallEvent.JoinCall(player, callNumber));
                    VoiceAddon.addToGroup(callNumber, player);
                }
            } else {
                if(!message.response) {

                    String callNumber = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(player));
                    MinecraftForge.EVENT_BUS.post(new CallEvent.LeaveCall(caller, callNumber));
                    if (caller != null) {
                        VoiceAddon.removeFromActualGroup(caller);
                    }
                    VoiceAddon.removeFromActualGroup(player);
                }else{
                    if(caller != null) {
                        String targetNum = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(caller));
                        Tuple<EntityPlayerMP, ItemStack> senderTuple = UtilsServer.getPlayerPhone(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), targetNum);
                        if (senderTuple == null) {
                            return null;
                        }
                        ItemStack receiverPhone = senderTuple.getSecond();
                        ItemPhone.setCall(receiverPhone, null, null, false);
                        SPhone.network.sendTo(new PacketStopSound("sphone:ringtone"), player);
                    }
                }
            }

            return null;
        }
    }
}
