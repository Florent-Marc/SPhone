package fr.sandji.sphone.mod.common.packets.server;


import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.items.ItemPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenContacts;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.server.bdd.MethodesBDDImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEditContact extends SerializablePacket implements IMessage {

    private String type;

    public PacketEditContact() {
    }

    public PacketEditContact(Contact contact, String type) {
        super(contact);
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

    public static class ServerHandler implements IMessageHandler<PacketEditContact, IMessage> {

        @Override
        public IMessage onMessage(PacketEditContact message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }

            Contact contact = (Contact) message.getObjectsIn()[0];
            String type = message.type;
            if (type.equals("edit")) {
                MethodesBDDImpl.editContact(contact);
            } else if (type.equals("add")) {
                MethodesBDDImpl.addContact(sim, contact);
            } else if(type.equals("delete")) {
                MethodesBDDImpl.deleteContact(contact);
            }
            SPhone.network.sendTo(new PacketOpenContacts(MethodesBDDImpl.getContacts(sim)), player);
            return null;
        }

    }
}
