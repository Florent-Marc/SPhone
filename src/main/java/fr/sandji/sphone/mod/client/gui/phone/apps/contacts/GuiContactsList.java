/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiContactsList extends GuiBase {

    public GuiContactsList(List<Contact> contacts) {
        super(new GuiHome().getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Contacts");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonAdd = new GuiLabel("+");
        ButtonAdd.setCssId("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewContact(contacts).getGuiScreen());
        });
        getBackground().add(ButtonAdd);

        /*GuiTextField SearchContact = new GuiTextField();
        SearchContact.setCssClass("search_contact");
        SearchContact.setHintText("➜ Rechercher");
        getBackground().add(SearchContact);*/

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 60, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (Contact contact : contacts) {

            GuiPanel contactPanel = new GuiPanel();
            contactPanel.setCssClass("contact_background");
            contactPanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiViewContact(contacts, contact).getGuiScreen());
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
