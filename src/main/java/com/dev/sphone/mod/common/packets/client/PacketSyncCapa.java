package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.common.capa.CapabilityHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSyncCapa implements IMessage {

    private int money;
    private int player;

    public PacketSyncCapa() {

    }

    public PacketSyncCapa(double money, int player) {
        this.money = (int) money;
        this.player = player;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        this.money = buf.readInt();
        this.player = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.money);
        buf.writeInt(this.player);
    }

    public static class Handler implements IMessageHandler<PacketSyncCapa, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSyncCapa message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            Minecraft.getMinecraft().addScheduledTask(() -> {
                Entity entity = player.world.getEntityByID(message.player);
                if(entity != null) {
                    if (entity instanceof EntityPlayer) {
                        entity.getCapability(CapabilityHandler.STATS, null).setMoney(message.money);
                    }
                }
            });
            return null;
        }
    }
}
