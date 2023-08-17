package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicechat.VoiceAddon;
import com.dev.sphone.api.voicechat.VoiceNetwork;
import com.dev.sphone.mod.common.packets.client.PacketCall;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.Utils;
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

public class PacketCallRequest implements IMessage {

    private boolean accept;
    private String numero;

    public PacketCallRequest() {
    }

    public PacketCallRequest(boolean accept, String numero) {
        this.numero = numero;
        this.accept = accept;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = buf.readBoolean();
        this.numero = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.accept);
        ByteBufUtils.writeUTF8String(buf, this.numero);
    }

    public static class ServerHandler implements IMessageHandler<PacketCallRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCallRequest message, MessageContext ctx) {
            //todo security
            EntityPlayer receiver = ctx.getServerHandler().player;
            EntityPlayer caller = VoiceNetwork.getPlayerFromNumber(message.numero);
            String CallNumber = MethodesBDDImpl.getNumero(Utils.getSimCard(receiver));
            if (message.accept) {
                MinecraftForge.EVENT_BUS.post(new CallEvent.JoinCall(receiver, CallNumber));
                VoiceAddon.addToGroup(CallNumber, receiver);
                SPhone.network.sendTo(new PacketCall(1, CallNumber), (EntityPlayerMP) caller);
                SPhone.network.sendTo(new PacketCall(1, CallNumber), (EntityPlayerMP) receiver);
            } else {
                MinecraftForge.EVENT_BUS.post(new CallEvent.LeaveCall(caller, CallNumber));
                if (caller!=null){
                    VoiceAddon.removeFromActualGroup(caller);
                    SPhone.network.sendTo(new PacketCall(0), (EntityPlayerMP) caller);
                }
                if (receiver!=null){
                    VoiceAddon.removeFromActualGroup(receiver);
                    SPhone.network.sendTo(new PacketCall(0), (EntityPlayerMP) receiver);
                };
            }

            return null;
        }
    }
}
