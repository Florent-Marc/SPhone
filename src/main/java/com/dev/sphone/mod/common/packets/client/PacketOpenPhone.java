package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.GuiNoSIM;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCall;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCallRequest;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiWaitCall;
import com.dev.sphone.mod.common.phone.Contact;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenPhone extends SerializablePacket implements IMessage {

    private String action;
    private String content;

    public PacketOpenPhone() {}

    public PacketOpenPhone(EnumAction action) {
        super(new Object[0]);
        this.action = action.name();
        this.content = "";
    }

    public PacketOpenPhone(EnumAction action, String content) {
        super(new Object[0]);
        this.action = action.name();
        this.content = content;
    }

    public PacketOpenPhone(EnumAction action, String content, Contact contact) {
        super(contact);
        this.action = action.name();
        this.content = content;

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
        this.content = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.content);
    }

    public static class Handler implements IMessageHandler<PacketOpenPhone, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenPhone message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    EnumAction action = EnumAction.valueOf(message.action);
                    switch (action) {
                        case HOME:
                            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
                            break;
                        case NOSIM:
                            Minecraft.getMinecraft().displayGuiScreen(new GuiNoSIM(message.content).getGuiScreen());
                            break;
                        case DONT_EXISTS:
                        case SEND_CALL:
                            Minecraft.getMinecraft().displayGuiScreen(new GuiCall(new GuiBase().getGuiScreen(), message.content).getGuiScreen());
                            break;
                        case WAIT_CALL:
                            Minecraft.getMinecraft().displayGuiScreen(new GuiWaitCall(new GuiBase().getGuiScreen(), message.content).getGuiScreen());
                            break;
                        case RECEIVE_CALL:
                            Minecraft.getMinecraft().displayGuiScreen(new GuiCallRequest(message.content, (Contact) message.getObjectsIn()[0]).getGuiScreen());
                            break;
                    }
                }
            });
            return null;
        }
    }

    public enum EnumAction {
        HOME,
        NOSIM,
        DONT_EXISTS,
        RECEIVE_CALL,
        WAIT_CALL,
        SEND_CALL;

    }
}

