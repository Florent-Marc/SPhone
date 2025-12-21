package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.phone.News;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketEditNews extends SerializablePacket implements IMessage {

    private String type;

    public PacketEditNews() {
    }

    public PacketEditNews(News news, String type) {
        super(news);
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

    public static class ServerHandler implements IMessageHandler<PacketEditNews, IMessage> {

        @Override
        public IMessage onMessage(PacketEditNews message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }

            News news = (News) message.getObjectsIn().get(0);
            String type = message.type;
            if (type.equals("edit")) {
                MethodesBDDImpl.getDatabaseInstance().editNews(news);
            } else if (type.equals("add")) {
                MethodesBDDImpl.getDatabaseInstance().addNews(news);
            } else if(type.equals("delete")) {
                MethodesBDDImpl.getDatabaseInstance().deleteNews(news);
            }
            // Todo : Ouvrir le GUI des News
            return null;
        }
    }
}
