
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.client;

import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Settings;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketOpenPhone implements IMessage {

    public PacketOpenPhone() {}

    public PacketOpenPhone(Settings settings, Contact contact) {}

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<PacketOpenPhone, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenPhone message, MessageContext ctx) {

            return null;
        }
    }

}

