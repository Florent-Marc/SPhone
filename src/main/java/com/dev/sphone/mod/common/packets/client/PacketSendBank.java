package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.apps.bank.GuiBank;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSendBank implements IMessage {

    public PacketSendBank() {}

    @Override
    public void fromBytes(ByteBuf buf) {
        // no data to read
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // no data to write
    }

    public static class Handler implements IMessageHandler<PacketSendBank, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSendBank message, MessageContext ctx) {
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(() -> {
                Minecraft mc = Minecraft.getMinecraft();
                GuiScreen parent = mc.currentScreen;
                mc.displayGuiScreen(new GuiBank(parent).getGuiScreen());
            });
            return null;
        }
    }

}