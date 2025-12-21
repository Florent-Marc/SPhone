package com.dev.sphone.mod.common.packets.server.radio;

import com.dev.sphone.mod.common.radio.RadioMuteManager;
import com.dev.sphone.mod.common.packets.server.HandlerTuneRadio;
import com.dev.sphone.mod.common.items.ItemRadio;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.item.ItemStack;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Packet sent from the client to toggle radio mute state.
 */
public class PacketRadioMuteToggle implements IMessage {

    private boolean muted;

    public PacketRadioMuteToggle() {}

    public PacketRadioMuteToggle(boolean muted) {
        this.muted = muted;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.muted = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.muted);
    }

    public static class Handler implements IMessageHandler<PacketRadioMuteToggle, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketRadioMuteToggle message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.getServerWorld().addScheduledTask(() -> {
                RadioMuteManager.setMuted(player.getUniqueID(), message.muted);

                // Update any radio items in the player's inventory with the appropriate lore
                for (ItemStack stack : player.inventory.mainInventory) {
                    if (stack != null && stack.getItem() == ItemsRegister.RADIO) {
                        ItemRadio.setMutedLore(stack, message.muted);
                    }
                }
                player.inventory.markDirty();

                if (!message.muted) {
                    int freq = HandlerTuneRadio.getPlayerFrequency(player);
                    if (freq > 0) {
                        java.util.Set<java.util.UUID> members = HandlerTuneRadio.RADIO_GROUPS.get(freq);
                        if (members != null) {
                            for (java.util.UUID id : members) {
                                if (id.equals(player.getUniqueID())) continue;
                                EntityPlayerMP target = player.getServer().getPlayerList().getPlayerByUUID(id);
                                if (target != null) {
                                    target.connection.sendPacket(new SPacketCustomSound(
                                            "sphone:radio_on", SoundCategory.MASTER,
                                            target.getPosition().getX(),
                                            target.getPosition().getY(),
                                            target.getPosition().getZ(),
                                            1f, 1f));
                                }
                            }
                        }
                    }
                }
            });
            return null;
        }
    }
}