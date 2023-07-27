
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.api.voicechat.SPhoneAddon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketQuitCall implements IMessage {



    public PacketQuitCall() {
    }


    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ServerHandler implements IMessageHandler<PacketQuitCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketQuitCall message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            SPhoneAddon.removeFromActualGroup(player);

            return null;
        }
    }

}
