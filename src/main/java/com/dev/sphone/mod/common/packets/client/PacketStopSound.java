package com.dev.sphone.mod.common.packets.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketStopSound implements IMessage {

    private String soundName;

    public PacketStopSound() {}

    public PacketStopSound(String soundName) {
        this.soundName = soundName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.soundName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.soundName);
    }

    public static class Handler implements IMessageHandler<PacketStopSound, IMessage> {
        @Override
        public IMessage onMessage(PacketStopSound message, MessageContext ctx) {

            Minecraft.getMinecraft().getSoundHandler().stop(message.soundName, net.minecraft.util.SoundCategory.MASTER);
            return null;
        }
    }
}

