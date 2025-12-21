package com.dev.sphone.mod.common.packets.client;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTuneRadio implements IMessage {

    private int frequency;

    public PacketTuneRadio() {}

    public PacketTuneRadio(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.frequency = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.frequency);
    }

    public int getFrequency() {
        return frequency;
    }

    // Handler interno (não usado para registro principal!)
    public static class Handler implements IMessageHandler<PacketTuneRadio, IMessage> {
        @Override
        public IMessage onMessage(PacketTuneRadio message, MessageContext ctx) {
            // O processamento REAL é feito em HandlerTuneRadio!
            return null;
        }
    }
}
