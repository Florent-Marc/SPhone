package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.api.voicechat.SPhoneAddon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCallRequest implements IMessage {

    private boolean accept;

    public PacketCallRequest() {
    }

    public PacketCallRequest( boolean accept) {
        this.accept = accept;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.accept = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.accept);
    }

    public static class ServerHandler implements IMessageHandler<PacketCallRequest, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketCallRequest message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            //get a itemphone and get tag numero
            String numero = SPhoneAddon.getGroup(player);
            if (message.accept) {
                SPhoneAddon.addToGroup(numero, player);
                //open gui ?
            }else {
                SPhoneAddon.closeCall(numero);
            }

            return null;
        }
    }
}
