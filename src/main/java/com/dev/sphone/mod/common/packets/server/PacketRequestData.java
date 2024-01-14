package com.dev.sphone.mod.common.packets.server;


import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenContacts;
import com.dev.sphone.mod.common.packets.client.PacketOpenListConv;
import com.dev.sphone.mod.common.packets.client.PacketOpenNotes;
import com.dev.sphone.mod.common.packets.client.PacketSendWeather;
import com.dev.sphone.mod.common.phone.Weather;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
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
            if (!(player.getHeldItemMainhand().getItem() instanceof ItemPhone)) {
                return null;
            }
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }
            if (!MethodesBDDImpl.isSimExist(sim)) {
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
