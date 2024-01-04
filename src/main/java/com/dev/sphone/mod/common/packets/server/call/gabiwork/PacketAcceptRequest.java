package com.dev.sphone.mod.common.packets.server.call.gabiwork;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.voicemanager.VoiceManager;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketAcceptRequest implements IMessage {

    public String numberTarget = "";
    public String contactName = "";
    public Boolean isAccepted = false;

    public PacketAcceptRequest() {
        this.numberTarget = "";
        this.contactName = "";

    }

    public PacketAcceptRequest(Boolean isAccepted, String numberTarget, String contactName) {
        this.isAccepted = isAccepted;
        this.numberTarget = numberTarget;
        this.contactName = contactName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.isAccepted = buf.readBoolean();
        this.numberTarget = ByteBufUtils.readUTF8String(buf);
        this.contactName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isAccepted);
        ByteBufUtils.writeUTF8String(buf, this.numberTarget);
        ByteBufUtils.writeUTF8String(buf, this.contactName);
    }

    public static class ServerHandler implements IMessageHandler<PacketAcceptRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketAcceptRequest message, MessageContext ctx) {
            EntityPlayerMP target = UtilsServer.getPlayerFromNumber(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), message.numberTarget);
            if (message.isAccepted) {
                VoiceManager.voiceManager.addPlayertoCall(ctx.getServerHandler().player, message.numberTarget);
                VoiceManager.voiceManager.addPlayertoCall(target, message.numberTarget);
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.SEND_CALL, message.contactName), target);
            } else {
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.DONT_EXISTS, message.numberTarget), target);
            }
            return null;
        }
    }
}