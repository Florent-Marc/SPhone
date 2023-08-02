package com.dev.sphone.mod.common.packets.server;

import akka.Main;
import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenConvContact;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.Utils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Date;

public class PacketSendMessage implements IMessage {

    private String message;
    private String sender;

    public PacketSendMessage() {
    }

    public PacketSendMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.message = ByteBufUtils.readUTF8String(buf);
        this.sender = ByteBufUtils.readUTF8String(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, message);
        ByteBufUtils.writeUTF8String(buf, sender);
    }

    //handler
    public static class ServerHandler implements IMessageHandler<PacketSendMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketSendMessage message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            String messageToSend = message.message;
            String receiver = message.sender;
            if (Utils.hasPhone(player)){
                String sender = String.valueOf(MethodesBDDImpl.getNumero(Utils.getSimCard(player)));
                Message message1 = new Message(messageToSend,new Date().getTime(),sender,receiver);
                MethodesBDDImpl.addMessage(message1);

                //SPhone.network.sendTo(new PacketOpenConvContact(MethodesBDDImpl.getConversations(Utils.getSimCard(player)), MethodesBDDImpl.getConversation(Utils.getSimCard(player), contact))),player);
            }

            return null;
        }
    }
}
