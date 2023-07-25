
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.client;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.client.gui.phone.apps.note.GuiNoteList;
import fr.sandji.sphone.mod.common.phone.Note;
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

import java.util.List;

public class PacketOpenNotes extends SerializablePacket implements IMessage {

    private String action;

    public PacketOpenNotes() {}

    public PacketOpenNotes(List<Note> note, String action) {
        super(note);
        this.action = action;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.action = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, this.action);
    }

    public static class Handler implements IMessageHandler<PacketOpenNotes, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenNotes message, MessageContext ctx) {
            List<Note> noteList = (List<Note>) message.getObjectsIn()[0];
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

