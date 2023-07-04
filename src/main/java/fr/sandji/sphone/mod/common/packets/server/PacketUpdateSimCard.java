

/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateSimCard implements IMessage {

    public String action;
    public String jsonFormat;

    public PacketUpdateSimCard() {}

    public PacketUpdateSimCard(String action, String jsonFormat) {
        this.action = action;
        this.jsonFormat = jsonFormat;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.action = ByteBufUtils.readUTF8String(buf);
        this.jsonFormat = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.jsonFormat);
    }

    public static class ServerHandler implements IMessageHandler<PacketUpdateSimCard, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketUpdateSimCard message, MessageContext ctx) {
            
            return null;
        }
    }

}
