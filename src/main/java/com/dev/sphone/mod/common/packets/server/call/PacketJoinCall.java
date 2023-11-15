package com.dev.sphone.mod.common.packets.server.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.CallEvent;
import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.api.voicemanager.voicechat.VoiceNetwork;
import com.dev.sphone.mod.common.packets.client.PacketCall;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import de.maxhenkel.voicechat.api.Group;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketJoinCall implements IMessage {

    private String number;

    public PacketJoinCall() {
    }

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

            return null;
        }
    }

}
