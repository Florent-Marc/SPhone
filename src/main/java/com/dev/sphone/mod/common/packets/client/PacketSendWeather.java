package com.dev.sphone.mod.common.packets.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import com.dev.sphone.mod.client.gui.phone.apps.weather.GuiWeather;
import com.dev.sphone.mod.common.phone.Weather;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketSendWeather extends SerializablePacket implements IMessage {

    public PacketSendWeather() {}

    public PacketSendWeather(Weather note) {
        super(note);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);
    }

    public static class Handler implements IMessageHandler<PacketSendWeather, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketSendWeather message, MessageContext ctx) {
            Weather weather = (Weather) message.getObjectsIn().get(0);
            IThreadListener thread = FMLCommonHandler.instance().getWorldThread(ctx.netHandler);
            thread.addScheduledTask(new Runnable() {
                public void run() {

                    Minecraft.getMinecraft().displayGuiScreen(new GuiWeather(new GuiHome().getGuiScreen(), weather).getGuiScreen());
                }
            });
            return null;
        }
    }

}

