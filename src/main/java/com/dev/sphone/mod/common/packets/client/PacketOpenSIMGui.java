package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import com.dev.sphone.mod.client.gui.phone.apps.settings.GuiSimEditor;
import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.common.phone.sim.SIMInventory;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
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

import java.util.List;

public class PacketOpenSIMGui extends SerializablePacket implements IMessage {

    public PacketOpenSIMGui() {}

    public PacketOpenSIMGui(int simId) {
        super(simId);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketOpenSIMGui, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenSIMGui message, MessageContext ctx) {
            System.out.println("PacketOpenSIMGui received on client : " + Minecraft.getMinecraft().player.getHeldItemMainhand());
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen(new GuiSimEditor(Minecraft.getMinecraft().player.inventory, new SIMInventory(Minecraft.getMinecraft().player.getHeldItemMainhand(), 1))));
            return null;
        }
    }

}

