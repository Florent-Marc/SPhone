package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketAcceptRequest;
import com.dev.sphone.mod.common.phone.Contact;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.server.call.PacketCallRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class GuiCallRequest extends GuiHome {

    private final String number;
    private final String contactTargetName;
    private final Contact contact;
    private final String receiver;

    public GuiCallRequest(String number, String targetName, Contact contact, String receiver) {
        super();
        this.number = number;
        this.contactTargetName = targetName;
        this.contact = contact;
        this.receiver = receiver;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        getBackground().removeAllChilds();
        GuiLabel label = new GuiLabel("Appel entrant");
        label.setCssId("app_title");
        getBackground().add(label);
        GuiLabel numberLabel = new GuiLabel(number);
        if(contact.getId() != -1) {
            numberLabel.setText(contact.getName() + " " + contact.getLastname());
        }

        numberLabel.setCssId("number");
        getBackground().add(numberLabel);

        GuiPanel ButtonAccept = new GuiPanel();
        ButtonAccept.setCssClass("button_accept");
        ButtonAccept.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketAcceptRequest(true, number, contactTargetName));
            mc.displayGuiScreen(new GuiCall(this.getGuiScreen(), contact.getName() + " " + contact.getLastname()).getGuiScreen());
        });
        getBackground().add(ButtonAccept);

        GuiPanel ButtonDecline = new GuiPanel();
        ButtonDecline.setCssClass("button_decline");
        ButtonDecline.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketCallRequest(false, receiver));
            mc.displayGuiScreen(new GuiCallEnd(this.getGuiScreen(), number).getGuiScreen());
        });
        getBackground().add(ButtonDecline);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/home.css"));
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/callrequest.css"));
        return styles;
    }
}
