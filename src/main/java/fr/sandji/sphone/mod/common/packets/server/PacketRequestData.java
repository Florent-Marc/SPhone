package fr.sandji.sphone.mod.common.packets.server;


import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.items.ItemPhone;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenContacts;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenListConv;
import fr.sandji.sphone.mod.common.packets.client.PacketOpenNotes;
import fr.sandji.sphone.mod.common.packets.client.PacketSendWeather;
import fr.sandji.sphone.mod.common.phone.Weather;
import fr.sandji.sphone.mod.server.bdd.MethodesBDDImpl;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketRequestData implements IMessage {

    private String type;

    public PacketRequestData() {
    }

    public PacketRequestData(String request) {
        this.type = request;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.type = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.type);
    }

    public static class ServerHandler implements IMessageHandler<PacketRequestData, IMessage> {

        @Override
        public IMessage onMessage(PacketRequestData message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }

            String request = message.type;

            if (request.equals("contacts")) {
                SPhone.network.sendTo(new PacketOpenContacts(MethodesBDDImpl.getContacts(sim)), player);
            }
            if (request.equals("notes")) {
                SPhone.network.sendTo(new PacketOpenNotes(MethodesBDDImpl.getNotes(sim)), player);
            }
            if(request.equals("weather")){
                WorldInfo worldInfo = player.world.getWorldInfo();
                SPhone.network.sendTo(new PacketSendWeather(new Weather(worldInfo.getCleanWeatherTime(), worldInfo.getRainTime(), worldInfo.getThunderTime(), worldInfo.isRaining(), worldInfo.isThundering())), player);
            }

            if(request.equals("conversations")){
                SPhone.network.sendTo(new PacketOpenListConv(MethodesBDDImpl.getConversations(sim)), player);
            }
            return null;
        }

    }
}
