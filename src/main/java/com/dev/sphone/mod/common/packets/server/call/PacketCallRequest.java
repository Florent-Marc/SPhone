package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
    private String targetNum;

    public PacketCallRequest() {
    }

    public PacketCallRequest(boolean accept, String targetNum) {
        this.accept = accept;
        this.targetNum = targetNum;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = buf.readBoolean();
        this.targetNum = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.accept);
        ByteBufUtils.writeUTF8String(buf, this.targetNum);
    }

    public static class ServerHandler implements IMessageHandler<PacketCallRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCallRequest message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            EntityPlayerMP caller = UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), message.targetNum);
            String callNumber = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(player));

            MinecraftForge.EVENT_BUS.post(new CallEvent.LeaveCall(caller, callNumber));
            if (caller != null) {
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.HOME), (EntityPlayerMP) caller);
                VoiceAddon.removeFromActualGroup(caller);
            }
            VoiceAddon.removeFromActualGroup(player);

            return null;
        }
    }
}
