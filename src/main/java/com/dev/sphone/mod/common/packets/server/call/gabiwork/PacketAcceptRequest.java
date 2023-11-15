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

public class PacketAcceptRequest implements IMessage {

    public String numberTarget = "";
    public Boolean isAccepted = false;

    public PacketAcceptRequest() {
        this.numberTarget = "";

    }

    public PacketAcceptRequest(Boolean isAccepted, String numberTarget) {
        this.isAccepted = isAccepted;
        this.numberTarget = numberTarget;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.numberTarget = ByteBufUtils.readUTF8String(buf);
        this.isAccepted = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        System.out.println(buf);
        ByteBufUtils.writeUTF8String(buf, this.numberTarget);
        buf.writeBoolean(this.isAccepted);
    }

    public static class ServerHandler implements IMessageHandler<PacketAcceptRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketAcceptRequest message, MessageContext ctx) {
            if (message.isAccepted) {
                if (VoiceManager.requestCallMap.containsKey(message.numberTarget)) {
                    System.out.println("onMessage : " + message.numberTarget);
                    VoiceManager.voiceManager.addPlayertoCall(ctx.getServerHandler().player, message.numberTarget);
                    VoiceManager.voiceManager.addPlayertoCall(UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), message.numberTarget), message.numberTarget);
                }
            } else {
                if (VoiceManager.requestCallMap.containsKey(message.numberTarget)) {
                    System.out.println("onMessage : " + message.numberTarget + " dontexists");
                    SPhone.network.sendTo(new PacketOpenPhone("dontexists", message.numberTarget), UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), message.numberTarget));
                    VoiceManager.requestCallMap.remove(message.numberTarget);
                }
            }
            return null;
        }
    }
}