package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.mod.utils.ObfuscateUtils;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetAnim implements IMessage {

    private boolean isAnim;

    public PacketSetAnim() {
    }

    public PacketSetAnim(boolean isAnim) {
        this.isAnim = isAnim;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        isAnim = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isAnim);
    }

    public static class ServerHandler implements IMessageHandler<PacketSetAnim, IMessage> {

        @Override
        public IMessage onMessage(PacketSetAnim message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            SyncedPlayerData.instance().set(player, ObfuscateUtils.PHOTO_MODE, message.isAnim);
            return null;
        }
    }
}
