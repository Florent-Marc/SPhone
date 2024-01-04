package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Objects;

public class PacketSendRequestCall implements IMessage {

    public String numberTarget = "";

    public PacketSendRequestCall() {
        this.numberTarget = "";
    }

    public PacketSendRequestCall(String numberTarget) {
        this.numberTarget = numberTarget;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.numberTarget = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.numberTarget);
    }

    public static class ServerHandler implements IMessageHandler<PacketSendRequestCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketSendRequestCall message, MessageContext ctx) {
            //todo security
            EntityPlayerMP sender = ctx.getServerHandler().player;

            boolean isUnknown = false;
            String senderNum = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(sender));
            String targetNum = message.numberTarget;

            if(targetNum.startsWith("#")) {
                // get 4 first chars
                String targetPhoneNumPrefix = targetNum.substring(0, 4);
                if(targetPhoneNumPrefix.equals("#31#")) {
                    targetNum = targetNum.substring(4);
                    isUnknown = true;
                }
            }
            if(Objects.equals(targetNum, "")) {
                return null;
            }

            String numSim = MethodesBDDImpl.getNumeroFromNumber(Integer.parseInt(targetNum));

            if(numSim == null){
                sender.connection.sendPacket(new SPacketCustomSound("sphone:nonattrib", SoundCategory.MASTER, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), 1f, 1f));
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.DONT_EXISTS, message.numberTarget), sender);
                return null;
            }
            if(numSim.equals(senderNum)){
                return null;
            }

            EntityPlayerMP receiver = UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), targetNum);
            if(receiver == null) {
                sender.connection.sendPacket(new SPacketCustomSound("sphone:unjoinable", SoundCategory.MASTER, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), 1f, 1f));
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.DONT_EXISTS, message.numberTarget), sender);
                return null;
            }

            receiver.connection.sendPacket(new SPacketCustomSound("sphone:ringtone", SoundCategory.MASTER, receiver.getPosition().getX(), receiver.getPosition().getY(), receiver.getPosition().getZ(), 1f, 1f));

            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Cherche contacts de SIM : " + UtilsServer.getSimCard(sender)));
            List<Contact> contacts = MethodesBDDImpl.getContacts(UtilsServer.getSimCard(sender));
            Contact contact = new Contact(-1, "Unknown", targetNum, targetNum, "");
            for (Contact cont : contacts) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Cherche: " + targetNum + TextFormatting.YELLOW +" ==> " + cont.toString()));
                if(cont.getNumero().equals(targetNum) && !isUnknown) {
                    contact = cont;
                    break;
                }
            }

            receiver.sendMessage(new TextComponentString(TextFormatting.RED + "Cherche contacts de SIM : " + UtilsServer.getSimCard(receiver)));
            List<Contact> contactsReceiver = MethodesBDDImpl.getContacts(UtilsServer.getSimCard(receiver));
            Contact contactReceiver = new Contact(-1, "Unknown", senderNum, senderNum, "");
            for (Contact cont : contactsReceiver) {
                receiver.sendMessage(new TextComponentString(TextFormatting.RED + "Cherche: " + senderNum + TextFormatting.YELLOW +" ==> " + cont.toString()));
                if(cont.getNumero().equals(senderNum) && !isUnknown) {
                    contactReceiver = cont;
                    break;
                }
            }

            SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.RECEIVE_CALL, senderNum, contactReceiver), receiver); // accept or deny message so, target
            SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.SEND_CALL, contact.getName() + " " + contact.getLastname()), sender); // player who wait

            return null;
        }
    }
}