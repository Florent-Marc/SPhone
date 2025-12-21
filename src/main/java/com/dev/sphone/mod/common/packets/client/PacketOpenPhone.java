package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.GuiNoSIM;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCall;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCallRequest;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiWaitCall;
import com.dev.sphone.mod.common.phone.Contact;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenPhone extends SerializablePacket implements IMessage {

    private String action;
    private String content;
    private String contactTargetName;
    private String receiver;

    public PacketOpenPhone() {}

    public PacketOpenPhone(EnumAction action) {
        super(new Object[0]);
        this.action = action.name();
        this.content = "";
        this.contactTargetName = "";
        this.receiver = "";
    }

    public PacketOpenPhone(EnumAction action, String content) {
        super(new Object[0]);
        this.action = action.name();
        this.content = content;
        this.contactTargetName = "";
        this.receiver = "";
    }

    public PacketOpenPhone(EnumAction action, String content, String receiver) {
        super(new Object[0]);
        this.action = action.name();
        this.content = content;
        this.contactTargetName = "";
        this.receiver = receiver;
    }

    public PacketOpenPhone(EnumAction action, String content, String contactTargetName, Contact contact) {
        super(contact);
        this.action = action.name();
        this.content = content;
        this.contactTargetName = contactTargetName;
        this.receiver = "";
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
        this.content = ByteBufUtils.readUTF8String(buf);
        this.contactTargetName = ByteBufUtils.readUTF8String(buf);
        this.receiver = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.content);
        ByteBufUtils.writeUTF8String(buf, this.contactTargetName);
        ByteBufUtils.writeUTF8String(buf, this.receiver);
    }

    public static class Handler implements IMessageHandler<PacketOpenPhone, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenPhone message, MessageContext ctx) {

            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    EnumAction action = EnumAction.valueOf(message.action);
                    System.out.println(message.action + " " + message.content + " " + message.contactTargetName + " " + message.receiver);
                    switch (action) {
                        case HOME:
                            mc.displayGuiScreen(new GuiHome().getGuiScreen());
                            break;
                        case NOSIM:
                            mc.displayGuiScreen(new GuiNoSIM(message.content).getGuiScreen());
                            break;
                        case DONT_EXISTS:
                        case OPEN_CALL:
                            mc.displayGuiScreen(new GuiCall(new GuiHome().getGuiScreen(), message.content).getGuiScreen());
                            break;

                        case WAIT_CALL:
                            mc.displayGuiScreen(new GuiWaitCall(new GuiHome().getGuiScreen(), message.content, message.receiver).getGuiScreen());
                            break;
                        case RECEIVE_CALL:
                            mc.displayGuiScreen(new GuiCallRequest(message.content, message.contactTargetName, (Contact) message.getObjectsIn().get(0), message.receiver).getGuiScreen());
                            break;
                        case CLOSED_SENDER:
                            if(mc.currentScreen instanceof GuiFrame.APIGuiScreen && ((GuiFrame.APIGuiScreen)mc.currentScreen).getFrame() instanceof GuiCallRequest){
                                mc.displayGuiScreen(new GuiHome().getGuiScreen());
                            }
                            break;
                        case SEND_CALL:
                            if(mc.currentScreen instanceof GuiFrame.APIGuiScreen && ((GuiFrame.APIGuiScreen)mc.currentScreen).getFrame() instanceof GuiBase){
                                mc.displayGuiScreen(new GuiCallRequest(message.content, message.contactTargetName, (Contact) message.getObjectsIn().get(0), message.receiver).getGuiScreen());
                            }else{

                            }
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
        CLOSED_SENDER,
        SEND_CALL,
        OPEN_CALL;

    }
}

