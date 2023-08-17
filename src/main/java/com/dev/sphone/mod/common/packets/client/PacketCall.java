package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCall;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCallEnd;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCallRequest;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCall implements IMessage {
    private int id;
    private String number;

    public PacketCall() {
    }

    public PacketCall(int id) {
        this.id = id;
        this.number = "";
    }

    public PacketCall(int id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.number = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        ByteBufUtils.writeUTF8String(buf, this.number);
    }

    public static class Handler implements IMessageHandler<PacketCall, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCall message, MessageContext ctx) {
            int id = message.id;
            if (id == 0) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiCallEnd(new GuiHome().getGuiScreen(), "").getGuiScreen());
            }
            if (id == 1) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiCall(new GuiHome().getGuiScreen(), message.number).getGuiScreen());
            }
            if (id == 2) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiCallRequest(message.number).getGuiScreen());
            }
            return null;
        }
    }

}

