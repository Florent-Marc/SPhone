package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import com.dev.sphone.mod.common.phone.Contact;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class PacketOpenContacts extends SerializablePacket implements IMessage {

    public PacketOpenContacts() {}

    public PacketOpenContacts(List<Contact> contact) {
        super(contact);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketOpenContacts, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenContacts message, MessageContext ctx) {
            List<Contact> contactList = (List<Contact>) message.getObjectsIn().get(0);
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiContactsList(new GuiHome().getGuiScreen(), contactList).getGuiScreen());
                }
            });
            return null;
        }
    }

}

