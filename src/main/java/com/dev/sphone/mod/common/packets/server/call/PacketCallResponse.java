package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.common.packets.client.PacketStopSound;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

public class PacketCallResponse implements IMessage {

    private String targetName;

    public PacketCallResponse() {
    }

    public PacketCallResponse(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.targetName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.targetName);
    }

    public static class ServerHandler implements IMessageHandler<PacketCallResponse, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCallResponse message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            EntityPlayer receiver = player.world.getPlayerEntityByName(message.targetName);

            if (receiver != null) {
                String targetNum = MethodesBDDImpl.getDatabaseInstance().getNumero(UtilsServer.getSimCard(receiver));
                Tuple<EntityPlayerMP, ItemStack> senderTuple = UtilsServer.getPlayerPhone(Objects.requireNonNull(ctx.getServerHandler().player.getServer()), targetNum);
                if (senderTuple == null) {
                    return null;
                }
                ItemStack receiverPhone = senderTuple.getSecond();
                ItemPhone.setCall(receiverPhone, null, null, false);
                SPhone.network.sendTo(new PacketStopSound("sphone:ringtone"), (EntityPlayerMP) receiver);
                SPhone.network.sendTo(new PacketOpenPhone(PacketOpenPhone.EnumAction.CLOSED_SENDER, player.getName()), (EntityPlayerMP) receiver);
            }

            return null;
        }
    }
}
