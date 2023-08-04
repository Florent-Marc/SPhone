package com.dev.sphone.mod.common.packets.client;

import de.maxhenkel.voicechat.VoicechatClient;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketPlayerHudState implements IMessage {

    private boolean showGroupHud;

    public PacketPlayerHudState() {}

    public PacketPlayerHudState(boolean showGroupHud) {
        this.showGroupHud = showGroupHud;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.showGroupHud = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.showGroupHud);
    }

    public static class Handler implements IMessageHandler<PacketPlayerHudState, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketPlayerHudState message, MessageContext ctx) {
            VoicechatClient.CLIENT_CONFIG.showGroupHUD.set(message.showGroupHud).save();
            return null;
        }
    }

}
