package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.api.voicemanager.voicechat.VoiceNetwork;
import com.dev.sphone.mod.common.packets.client.PacketCall;
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

public class PacketQuitCall implements IMessage {
    private String numero;

    public PacketQuitCall(String numero) {
        this.numero = numero;
    }

    public PacketQuitCall() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.numero = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.numero);
    }

    public static class ServerHandler implements IMessageHandler<PacketQuitCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketQuitCall message, MessageContext ctx) {
//            String CallNumber = message.numero;
//            EntityPlayer receiver = VoiceNetwork.getPlayerFromNumber(CallNumber);
//            EntityPlayer caller = VoiceAddon.getCallerInGroup(CallNumber, receiver);
//            if (receiver == null || caller == null) {
//                return null;
//            }
//            VoiceAddon.removeFromActualGroup(receiver);
//            VoiceAddon.removeFromActualGroup(caller);
//            MinecraftForge.EVENT_BUS.post(new CallEvent.LeaveCall(receiver, CallNumber));
//            MinecraftForge.EVENT_BUS.post(new CallEvent.LeaveCall(caller, CallNumber));
//
//            SPhone.network.sendTo(new PacketCall(0), (EntityPlayerMP) receiver);
//            SPhone.network.sendTo(new PacketCall(0), (EntityPlayerMP) caller);
            return null;
        }
    }

}
