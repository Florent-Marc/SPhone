package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.apps.message.GuiConv;
import com.dev.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import com.dev.sphone.mod.common.phone.Conversation;
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

public class PacketOpenConvContact extends SerializablePacket implements IMessage {

    public PacketOpenConvContact() {}

    public PacketOpenConvContact(List<Conversation> convList, Conversation conv) {
        super(convList, conv);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketOpenConvContact, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenConvContact message, MessageContext ctx) {
            List<Conversation> convList = (List<Conversation>) message.getObjectsIn().get(0);
            Conversation conv = (Conversation) message.getObjectsIn().get(1);
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConv(new GuiConvList(convList).getGuiScreen(), conv).getGuiScreen());
                }
            });
            return null;
        }
    }

}

