package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.api.events.MessageEvent;
import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.phone.Conversation;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.UtilsServer;
import fr.aym.acslib.utils.packetserializer.SerializablePacket;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Date;
import java.util.Objects;



public class PacketManageApp extends SerializablePacket implements IMessage {
    public enum Actions {
        INSTALL,
        UNINSTALL
    }


    public PacketManageApp() {
    }

    public PacketManageApp(Actions action, String appname) {
        super(action, appname);
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        super.fromBytes(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        super.toBytes(buf);

    }

    public static class ServerHandler implements IMessageHandler<PacketManageApp, IMessage> {
        @Override
        public IMessage onMessage(PacketManageApp message, MessageContext ctx) {

            ItemStack phoneStack = ctx.getServerHandler().player.getHeldItemMainhand();
            Actions action = (Actions) message.getObjectsIn().get(0);
            String appname = (String) message.getObjectsIn().get(1);
            if(phoneStack.getTagCompound() == null) {
                phoneStack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound tag = phoneStack.getTagCompound();

            NBTTagCompound apps = tag.getCompoundTag("apps");
            if(action.equals(Actions.INSTALL)) {
                apps.setString(appname, "1");
            } else if(action.equals(Actions.UNINSTALL)) {
                apps.removeTag(appname);
            }
            tag.setTag("apps", apps);
            phoneStack.setTagCompound(tag);

            return null;
        }
    }
}
