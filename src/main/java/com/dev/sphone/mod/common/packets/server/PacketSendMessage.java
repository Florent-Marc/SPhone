package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.MessageEvent;
import com.dev.sphone.mod.common.packets.client.PacketOpenConvContact;
import com.dev.sphone.mod.common.phone.Conversation;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.Utils;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Date;

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
            Conversation receiver = (Conversation) message.getObjectsIn()[0];
            if (Utils.hasPhone(player)) {
                if (Utils.getSimCard(player) == 0) {
                    return null;
                }
                String sender = String.valueOf(MethodesBDDImpl.getNumero(Utils.getSimCard(player)));
                Message message1 = new Message(messageToSend, new Date().getTime(), sender, receiver.getSender().getNumero());
                MinecraftForge.EVENT_BUS.post(new MessageEvent.Send(sender, message1));
                MethodesBDDImpl.addMessage(message1);
            }

            return null;
        }
    }
}
