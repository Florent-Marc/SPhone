package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Tuple;
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
    public String contactName = "";

    public PacketSendRequestCall() {
        this.numberTarget = "";
        this.contactName = "";
    }

    public PacketSendRequestCall(String numberTarget) {
        this.numberTarget = numberTarget;
        this.contactName = "";
    }

    public PacketSendRequestCall(String numberTarget, String contactName) {
        this.numberTarget = numberTarget;
        this.contactName = contactName;
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
            if(targetNum.equals(senderNum)){
                return null;
            }

            String simReceiver = MethodesBDDImpl.getSimFromNum(Integer.parseInt(targetNum));

            if(simReceiver == null){
                sender.connection.sendPacket(new SPacketCustomSound("sphone:nonattrib", SoundCategory.MASTER, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), 1f, 1f));
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.DONT_EXISTS, message.numberTarget), sender);
                return null;
            }


            Tuple<EntityPlayerMP, ItemStack> receiverTuple = UtilsServer.getPlayerPhone(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), targetNum);
            if(receiverTuple == null){
                sender.connection.sendPacket(new SPacketCustomSound("sphone:unjoinable", SoundCategory.MASTER, sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), 1f, 1f));
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.DONT_EXISTS, message.contactName.isEmpty() ? message.numberTarget : message.contactName), sender);
                return null;
            }
            EntityPlayerMP receiver = receiverTuple.getFirst();
            ItemStack receiverPhone = receiverTuple.getSecond();

            receiver.connection.sendPacket(new SPacketCustomSound("sphone:ringtone", SoundCategory.MASTER, receiver.getPosition().getX(), receiver.getPosition().getY(), receiver.getPosition().getZ(), 1f, 1f));

            Contact contact = new Contact(-1, "Unknown", "", targetNum, "");
            if(message.contactName.isEmpty()) {
                List<Contact> contacts = MethodesBDDImpl.getContacts(UtilsServer.getSimCard(sender));
                for (Contact cont : contacts) {
                    if (cont.getNumero().equals(targetNum) && !isUnknown) {
                        contact = cont;
                        break;
                    }
                }
            }
            ItemPhone.setCall(receiverPhone, senderNum, contact.getName() + " " + contact.getLastname(), isUnknown);

            //SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.RECEIVE_CALL, senderNum, contactReceiver), receiver);
            SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.WAIT_CALL, message.contactName.isEmpty() ? contact.getName() + " " + contact.getLastname() : message.contactName, receiver.getName()), sender); // player who wait

            return null;
        }
    }
}