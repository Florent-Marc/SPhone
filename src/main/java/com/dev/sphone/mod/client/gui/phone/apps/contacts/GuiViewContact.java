package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.packets.server.PacketGetUniqueConv;
import com.dev.sphone.mod.common.packets.server.call.PacketJoinCall;
import com.dev.sphone.mod.common.phone.Contact;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiViewContact extends GuiBase {

    private final Contact contact;

    public GuiViewContact(GuiScreen parent, Contact contact) {
        super(parent);
        this.contact = contact;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(getRoot());

        GuiLabel appTitle = new GuiLabel("Contact");
        appTitle.setCssId("app_title");
        getRoot().add(appTitle);

        GuiLabel buttonEdit = new GuiLabel("✎");
        buttonEdit.setCssId("button_add");
        getRoot().add(buttonEdit);
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditContact(this.getGuiScreen(), contact).getGuiScreen());
        });

        GuiPanel message = new GuiPanel();
        message.setCssClass("message");
        getRoot().add(message);
        message.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketGetUniqueConv(contact));
        });

        GuiPanel call = new GuiPanel();
        call.setCssClass("call");
        getRoot().add(call);
        call.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketJoinCall(contact.getNumero()));
        });

        GuiLabel contactAvatar = new GuiLabel("");
        contactAvatar.setCssId("view_contact_avatar");
        String cssCode = "background-image: url(\"sphone:textures/ui/icons/nohead.png\");";
        contactAvatar.setCssCode("view_contact_avatar", cssCode);
        getRoot().add(contactAvatar);

        GuiLabel name = new GuiLabel("Nom : " + contact.getName());
        name.setCssId("name");
        getRoot().add(name);

        GuiLabel lastname = new GuiLabel("Prénom : " + (contact.getLastname().isEmpty() ? "Non renseigné" : contact.getLastname()));
        lastname.setCssId("lastname");
        getRoot().add(lastname);

        GuiLabel phone = new GuiLabel("Numéro : " + contact.getNumero());
        phone.setCssId("phone");
        getRoot().add(phone);

        if (!contact.getNotes().isEmpty()) {
            GuiLabel notes = new GuiLabel("Note : " + contact.getNotes());
            notes.setCssId("notes");
            getRoot().add(notes);
        }
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contacts.css"));
        return styles;
    }

}
