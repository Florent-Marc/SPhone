package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicechat.SPhoneAddon;
import de.maxhenkel.voicechat.api.Group;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketJoinCall implements IMessage {

    private String number;

    public PacketJoinCall() {}

    public PacketJoinCall(String number) {
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.number = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.number);
    }

    public static class ServerHandler implements IMessageHandler<PacketJoinCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketJoinCall message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            MinecraftForge.EVENT_BUS.post(new CallEvent.JoinCall(player, message.number));
            if(SPhoneAddon.groupExists(message.number)){
                SPhoneAddon.addToGroup(message.number, player);
            }else {
                SPhoneAddon.createGroup(message.number,false, Group.Type.ISOLATED);
                SPhoneAddon.addToGroup(message.number, player);
            }
            
            return null;
        }
    }

}
