package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketToggleGroupMute implements IMessage {
    private boolean mute;

    public PacketToggleGroupMute() {}

    public PacketToggleGroupMute(boolean mute) {
        this.mute = mute;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.mute = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.mute);
    }

    public static class ServerHandler implements IMessageHandler<PacketToggleGroupMute, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketToggleGroupMute message, MessageContext ctx) {
                if (!com.dev.sphone.SPhone.isModLoaded("voicechat")) {
                return null;
            }
            EntityPlayerMP player = ctx.getServerHandler().player;
            VoiceAddon.setGroupMute(player, message.mute);
            return null;
        }
    }
}