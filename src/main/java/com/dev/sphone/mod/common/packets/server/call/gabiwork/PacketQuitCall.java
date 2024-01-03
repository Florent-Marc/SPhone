package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketQuitCall implements IMessage {

    public String numberTarget = "";

    public PacketQuitCall() {
        this.numberTarget = "";
    }

    public PacketQuitCall(String numberTarget) {
        this.numberTarget = numberTarget;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.numberTarget = ByteBufUtils.readUTF8String(buf);
        System.out.println("fromBytes : " + this.numberTarget);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        System.out.println(buf);
        ByteBufUtils.writeUTF8String(buf, this.numberTarget);
    }

    public static class ServerHandler implements IMessageHandler<PacketQuitCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketQuitCall message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            VoiceAddon.removeFromActualGroup(player);
            /*
            if(VoiceManager.callMap.containsKey(message.numberTarget)) {
                List<EntityPlayerMP> players = VoiceManager.callMap.get(message.numberTarget);
                if(players.contains(player)) {
                    VoiceManager.callMap.remove(message.numberTarget);
                    VoiceManager.voiceManager.removePlayerFromCall(player);
                    for(EntityPlayerMP p : players) {
                        if(p != player) {
                            VoiceManager.voiceManager.removePlayerFromCall(p);
                        }
                    }
                }
            }*/




            return null;
        }
    }
}