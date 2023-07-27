package fr.sandji.sphone.mod.common.packets.client;

import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import fr.sandji.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import fr.sandji.sphone.mod.common.phone.Conversation;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            List<Conversation> conversations = (List<Conversation>) message.getObjectsIn()[0];

            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConvList(conversations).getGuiScreen());
                }
            });
            return null;
        }
    }

}

