
package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import com.dev.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiContactsList extends GuiBase {

    private final List<Contact> contacts;

    public GuiContactsList(GuiScreen parent, List<Contact> contacts) {
        super(parent);
        this.contacts = contacts;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        GuiLabel AppTitle = new GuiLabel("Contacts");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonAdd = new GuiLabel("+");
        ButtonAdd.setCssId("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewContact(this.getGuiScreen()).getGuiScreen());
        });
        getBackground().add(ButtonAdd);

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 60, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (Contact contact : contacts) {

            GuiPanel contactPanel = new GuiPanel();
            contactPanel.setCssClass("contact_background");
            contactPanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiViewContact(this.getGuiScreen(), contact).getGuiScreen());
            });

            GuiPanel ContactAvatar = new GuiPanel();
            ContactAvatar.setCssClass("contact_avatar");
            //String cssCode = "background-image: url(\"https://mc-heads.net/avatar/" + contact.getPlayer_associated() + "\");";
            //ContactAvatar.setCssCode("contact_avatar", cssCode);
            contactPanel.add(ContactAvatar);

            GuiLabel ContactName = new GuiLabel(contact.getName() + " " + contact.getLastname());
            ContactName.setCssId("contact_name");
            contactPanel.add(ContactName);

            contacts_list.add(contactPanel);
        }

        getBackground().add(contacts_list);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contactslist.css"));
        return styles;
    }

}
