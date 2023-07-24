package fr.sandji.sphone.mod.common.packets.server;



import fr.sandji.sphone.mod.server.bdd.MethodesBDDImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestData implements IMessage {

    private String accept;

    public PacketRequestData() {
    }

    public PacketRequestData(String request) {
        this.accept = request;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.accept);
    }

    public static class ServerHandler implements IMessageHandler<PacketRequestData, IMessage> {

        @Override
        public IMessage onMessage(PacketRequestData message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            String request = message.accept;
            if (request.equals("getContact")) {
                MethodesBDDImpl.getContact(1111);
                //packet to client
            }
            return null;
        }

    }
}
