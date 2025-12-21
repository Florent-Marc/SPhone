package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.phone.Conversation;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PacketOpenListConv extends SerializablePacket implements IMessage {

    public PacketOpenListConv() {}

    public PacketOpenListConv(List<Conversation> conversations) {
        super(conversations);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketOpenListConv, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenListConv message, MessageContext ctx) {
            List<Conversation> conversations = (List<Conversation>) message.getObjectsIn().get(0);
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {
                    if (conversations.isEmpty()) {
                        int sim = ItemPhone.getSimCard(Minecraft.getMinecraft().player.getHeldItemMainhand());
                        if (sim == 0) {
                            Minecraft.getMinecraft().displayGuiScreen(new GuiConvList(new ArrayList<>()).getGuiScreen());
                            return;
                        }else {
                            List<Conversation> convs = new ArrayList<>();
                            convs = ClientEventHandler.conversations.get(sim);
                            Minecraft.getMinecraft().displayGuiScreen(new GuiConvList(convs).getGuiScreen());
                        }
                    }else {
                        Minecraft.getMinecraft().displayGuiScreen(new GuiConvList(conversations).getGuiScreen());
                        int sim = ItemPhone.getSimCard(Minecraft.getMinecraft().player.getHeldItemMainhand());
                        ClientEventHandler.conversations.put(sim,conversations);
                    }
                }
            });

            return null;
        }
    }

}

