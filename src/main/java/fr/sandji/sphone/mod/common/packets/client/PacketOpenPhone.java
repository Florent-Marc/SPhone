
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.packets.client;

import com.google.gson.Gson;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import fr.sandji.sphone.mod.client.tempdata.PhoneSettings;
import fr.sandji.sphone.mod.common.phone.Contact;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PacketOpenPhone implements IMessage {

    private String action;
    private String content;

    public PacketOpenPhone() {}

    public PacketOpenPhone(String action, String content) {
        this.action = action;
        this.content = content;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.action = ByteBufUtils.readUTF8String(buf);
        this.content = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.action);
        ByteBufUtils.writeUTF8String(buf, this.content);
    }

    public static class Handler implements IMessageHandler<PacketOpenPhone, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketOpenPhone message, MessageContext ctx) {
            if (message.action.equals("Home")) {
                if (PhoneSettings.background != null) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
                } else {

                }
            }
            if (message.action.equals("openContacts")) {
                List<Contact> contactList = new ArrayList<Contact>();
                Gson gson = new Gson();
                Contact[] contacts = gson.fromJson(message.content, Contact[].class);
                for (Contact contact : contacts) {
                    contactList.add(new Contact(contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getPlayer_associated()));
                }
                Minecraft.getMinecraft().displayGuiScreen(new GuiContactsList(contactList).getGuiScreen());
            }
            return null;
        }
    }

}

