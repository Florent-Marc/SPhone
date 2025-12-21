package com.dev.sphone.mod.common.packets.server;


import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenNotes;
import com.dev.sphone.mod.common.phone.Note;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import com.dev.sphone.SPhone;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEditNote extends SerializablePacket implements IMessage {

    private String type;

    public PacketEditNote() {
    }

    public PacketEditNote(Note note, String type) {
        super(note);
        this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.type = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, this.type);
    }

    public static class ServerHandler implements IMessageHandler<PacketEditNote, IMessage> {

        @Override
        public IMessage onMessage(PacketEditNote message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }

            Note note = (Note) message.getObjectsIn().get(0);
            String type = message.type;
            if (type.equals("edit")) {
                MethodesBDDImpl.getDatabaseInstance().editNote(note);
            } else if (type.equals("add")) {
                MethodesBDDImpl.getDatabaseInstance().addNote(sim, note);
            } else if(type.equals("delete")) {
                MethodesBDDImpl.getDatabaseInstance().deleteNote(note);
            }
            SPhone.network.sendTo(new PacketOpenNotes(MethodesBDDImpl.getDatabaseInstance().getNotes(sim)), player);
            return null;
        }

    }
}
