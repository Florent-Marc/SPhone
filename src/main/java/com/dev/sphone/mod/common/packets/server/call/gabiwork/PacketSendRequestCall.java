package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.voicemanager.VoiceManager;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        System.out.println("fromBytes : " + this.numberTarget);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        System.out.println(buf);
        ByteBufUtils.writeUTF8String(buf, this.numberTarget);
    }

    public static class ServerHandler implements IMessageHandler<PacketSendRequestCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketSendRequestCall message, MessageContext ctx) {
            //todo security
            EntityPlayerMP sender = ctx.getServerHandler().player;

            boolean isUnknown = false;
            String targetPhoneNum = message.numberTarget;

            if(targetPhoneNum.startsWith("#")) {
                // get 4 first chars
                String targetPhoneNumPrefix = targetPhoneNum.substring(0, 4);
                if(targetPhoneNumPrefix.equals("#31#")) {
                    targetPhoneNum = targetPhoneNum.substring(4);
                    isUnknown = true;
                }
            }



            if(Objects.equals(targetPhoneNum, "")) {
                return null;
            }

            if(MethodesBDDImpl.getNumeroFromNumber(Integer.parseInt(targetPhoneNum)) == null){
                sender.connection.sendPacket(new SPacketCustomSound("sphone:nonattrib",
                        SoundCategory.MASTER,
                        sender.getPosition().getX(),
                        sender.getPosition().getY(),
                        sender.getPosition().getZ(),
                        1f,
                        1f
                ));
                SPhone.network.sendTo(new PacketOpenPhone("dontexists", message.numberTarget), sender);

                return null;
            }

            if(MethodesBDDImpl.getNumeroFromNumber(Integer.parseInt(targetPhoneNum)).equals(MethodesBDDImpl.getNumero(UtilsServer.getSimCard(sender)))){
                return null;
            }



            String playercalling = MethodesBDDImpl.getNumero(UtilsServer.getSimCard(sender));
            EntityPlayerMP receiver = UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), targetPhoneNum);
            if(receiver == null) {
                sender.connection.sendPacket(new SPacketCustomSound("sphone:unjoinable",
                        SoundCategory.MASTER,
                        sender.getPosition().getX(),
                        sender.getPosition().getY(),
                        sender.getPosition().getZ(),
                        1f,
                        1f
                ));
                SPhone.network.sendTo(new PacketOpenPhone("dontexists", message.numberTarget), sender);

                return null;
            }

            receiver.connection.sendPacket(new SPacketCustomSound("sphone:ringtone",
                    SoundCategory.MASTER,
                    receiver.getPosition().getX(),
                    receiver.getPosition().getY(),
                    receiver.getPosition().getZ(),
                    1f,
                    1f
            ));

            VoiceManager.requestCallMap.put(playercalling, targetPhoneNum);


            SPhone.network.sendTo(new PacketOpenPhone("recievecall", isUnknown ? "Unknown" : playercalling ), receiver); // accept or deny message so, target
            SPhone.network.sendTo(new PacketOpenPhone("sendcall", targetPhoneNum), sender); // player who wait

            return null;
        }
    }
}