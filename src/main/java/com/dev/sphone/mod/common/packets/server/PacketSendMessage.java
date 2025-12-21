package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.api.events.MessageEvent;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.phone.Conversation;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Date;
import java.util.Objects;

public class PacketSendMessage extends SerializablePacket implements IMessage {

    private String message;

    public PacketSendMessage() {
    }

    public PacketSendMessage(String message, Conversation sender) {
        super(sender);
        this.message = message;

    }


    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
        this.message = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
        ByteBufUtils.writeUTF8String(buf, message);
    }

    public static class ServerHandler implements IMessageHandler<PacketSendMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketSendMessage message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            String messageToSend = message.message;
            Conversation receiverConv = (Conversation) message.getObjectsIn().get(0);
            int sim = ItemPhone.getSimCard(player.getHeldItemMainhand());
            if (sim == 0) {
                return null;
            }
            String sender = String.valueOf(MethodesBDDImpl.getDatabaseInstance().getNumero(UtilsServer.getSimCard(player)));
            Message message1 = new Message(messageToSend, new Date().getTime(), sender, receiverConv.getSender().getNumero());
            EntityPlayerMP receiverTarget = UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), receiverConv.getSender().getNumero());
            if (!MinecraftForge.EVENT_BUS.post(new MessageEvent.Send(sender,player,receiverConv.getSender().getNumero(),receiverTarget, message1))) {
                MethodesBDDImpl.getDatabaseInstance().addMessage(message1);
                if (player.getServer() != null) {
                    if (receiverTarget != null) {
                        receiverTarget.connection.sendPacket(new SPacketCustomSound("sphone:notif", SoundCategory.MASTER, receiverTarget.getPosition().getX(), receiverTarget.getPosition().getY(), receiverTarget.getPosition().getZ(), 1f, 1f));
                    }
                }
            }

            return null;
        }
    }
}
