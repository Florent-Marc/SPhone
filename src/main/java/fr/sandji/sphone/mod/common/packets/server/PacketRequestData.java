package fr.sandji.sphone.mod.common.packets.server;


import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenNotes;
import fr.sandji.sphone.mod.server.bdd.MethodesBDDImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestData implements IMessage {

    private String accept;
    private String sim;

    public PacketRequestData() {
    }

    public PacketRequestData(String request, String sim) {
        this.accept = request;
        this.sim = sim;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = ByteBufUtils.readUTF8String(buf);
        this.sim = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.accept);
        ByteBufUtils.writeUTF8String(buf, this.sim);
    }

    public static class ServerHandler implements IMessageHandler<PacketRequestData, IMessage> {

        @Override
        public IMessage onMessage(PacketRequestData message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            String request = message.accept;
            if (request.equals("getContact")) {
                MethodesBDDImpl.getContact(Integer.parseInt(message.sim));
                //packet to client
            }
            return null;
        }

    }
}
