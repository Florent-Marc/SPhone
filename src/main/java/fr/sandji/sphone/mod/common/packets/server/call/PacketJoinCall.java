
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.server.call;

import de.maxhenkel.voicechat.api.Group;
import fr.sandji.sphone.api.voicechat.SPhoneAddon;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketJoinCall implements IMessage {

    private int number;

    public PacketJoinCall() {}

    public PacketJoinCall(int number) {
        this.number = number;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.number = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.number);
    }

    public static class ServerHandler implements IMessageHandler<PacketJoinCall, IMessage> {
        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(PacketJoinCall message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            if(SPhoneAddon.groupExists(String.valueOf(message.number))){
                SPhoneAddon.addToGroup(String.valueOf(message.number), player);
            }else {
                SPhoneAddon.createGroup(String.valueOf(message.number),false, Group.Type.ISOLATED);
                SPhoneAddon.addToGroup(String.valueOf(message.number), player);
            }
            
            return null;
        }
    }

}
