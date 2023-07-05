package fr.sandji.sphone.mod.common.packets.client;

import fr.sandji.sphone.mod.client.gui.phone.apps.call.GuiCall;
import fr.sandji.sphone.mod.client.gui.phone.apps.call.GuiCallEnd;
import fr.sandji.sphone.mod.client.gui.phone.apps.call.GuiCallRequest;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Settings;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCall implements IMessage {
    private int id;
    private String number;

    public PacketCall() {}

    public PacketCall(int id) {
        this.id = id;
        this.number = "";
    }

    public PacketCall(int id,String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.id = buf.readInt();
        this.number = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.id);
        ByteBufUtils.writeUTF8String(buf, this.number);
    }

    public static class Handler implements IMessageHandler<PacketCall, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketCall message, MessageContext ctx) {
            int id = message.id;
            if(id == 0){
                Minecraft.getMinecraft().displayGuiScreen(new GuiCallEnd("").getGuiScreen());
            }
            if(id == 1){
                Minecraft.getMinecraft().displayGuiScreen(new GuiCall(message.number).getGuiScreen());
            }
            if(id == 2){
                //song and set call enter in tel
                Minecraft.getMinecraft().displayGuiScreen(new GuiCallRequest(message.number).getGuiScreen());
            }

            return null;
        }
    }

}

