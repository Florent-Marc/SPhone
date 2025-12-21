package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.apps.note.GuiNoteList;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import com.dev.sphone.mod.common.phone.Note;
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

public class PacketOpenNotes extends SerializablePacket implements IMessage {

    public PacketOpenNotes() {}

    public PacketOpenNotes(List<Note> note) {
        super(note);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketOpenNotes, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenNotes message, MessageContext ctx) {
            List<Note> noteList = (List<Note>) message.getObjectsIn().get(0);
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiNoteList(new GuiHome().getGuiScreen(), noteList).getGuiScreen());
                }
            });
            return null;
        }
    }

}

