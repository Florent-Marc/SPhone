package com.dev.sphone.mod.common.packets.server;


import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenConvContact;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetUniqueConv extends SerializablePacket implements IMessage {
    public PacketGetUniqueConv() {
    }

    public PacketGetUniqueConv(Contact contact) {
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

    public static class ServerHandler implements IMessageHandler<PacketGetUniqueConv, IMessage> {

        @Override
        public IMessage onMessage(PacketGetUniqueConv message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }

            Contact contact = (Contact) message.getObjectsIn().get(0);

            SPhone.network.sendTo(new PacketOpenConvContact(MethodesBDDImpl.getDatabaseInstance().getConversations(sim), MethodesBDDImpl.getDatabaseInstance().getConversation(sim, contact)), player);
            
            return null;
        }

    }
}
