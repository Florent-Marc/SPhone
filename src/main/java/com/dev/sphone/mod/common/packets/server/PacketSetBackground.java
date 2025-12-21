package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;


public class PacketSetBackground extends SerializablePacket implements IMessage {


    public PacketSetBackground() {
    }

    public PacketSetBackground(String bgname) {
        super(bgname);
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

    }

    public static class ServerHandler implements IMessageHandler<PacketSetBackground, IMessage> {
        @Override
        public IMessage onMessage(PacketSetBackground message, MessageContext ctx) {

            PhoneSettings settings = new PhoneSettings("acsgui");
            ItemStack stack = ctx.getServerHandler().player.getHeldItemMainhand();
            if (stack.getItem() instanceof ItemPhone) {
                settings.deserializeNBT(Objects.requireNonNull(ctx.getServerHandler().player.getHeldItemMainhand().getTagCompound()).getCompoundTag("settings"));
            }
            settings.setBackground((String) message.getObjectsIn().get(0));
            Objects.requireNonNull(ctx.getServerHandler().player.getHeldItemMainhand().getTagCompound()).setTag("settings", settings.serializeNBT());

            return null;
        }
    }
}
