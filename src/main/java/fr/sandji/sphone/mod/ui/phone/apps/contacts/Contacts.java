/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.ui.phone.apps.contacts;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.event.listeners.mouse.IMouseMoveListener;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.ui.phone.Home;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Contacts extends GuiFrame {

    public Contacts(List<Contact> contacts) {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        GuiLabel Background = new GuiLabel("");
        Background.setCssId("background");
        add(Background);

        GuiLabel Case = new GuiLabel("");
        Case.setCssId("case");
        add(Case);

        GuiLabel TopClock = new GuiLabel("");
        TopClock.setCssId("top_clock");
        TopClock.addTickListener(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            TopClock.setText(dateFormat.format(date));
        });
        add(TopClock);

        GuiLabel TopIcons = new GuiLabel("");
        TopIcons.setCssId("top_icons");
        add(TopIcons);

        GuiLabel HomeBar = new GuiLabel("");
        HomeBar.setCssId("home_bar");
        HomeBar.addClickListener((x,y,bu) -> {
            Minecraft.getMinecraft().displayGuiScreen(new Home().getGuiScreen());
        });
        add(HomeBar);

        GuiLabel AppTitle = new GuiLabel("Contacts");
        AppTitle.setCssId("app_title");
        add(AppTitle);

        GuiLabel ButtonAdd = new GuiLabel("+");
        ButtonAdd.setCssId("button_add");
        add(ButtonAdd);

        GuiTextField SearchContact = new GuiTextField();
        SearchContact.setCssId("search_contact");
        SearchContact.setHintText("➜ Rechercher");
        add(SearchContact);

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssId("contacts_list");
        //contacts_list.setLayout(new GridLayout(-1, 10, 5, GridLayout.GridDirection.VERTICAL, 1));
        contacts_list.setLayout(new GridLayout(-1, 60, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (Contact contact : contacts) {

            GuiPanel contactPanel = new GuiPanel();
            contactPanel.setCssClass("contact_background");

            GuiPanel ContactAvatar = new GuiPanel();
            ContactAvatar.setCssClass("contact_avatar");
            String cssCode = "background-image: url(\"https://mc-heads.net/avatar/" + contact.getPlayer_associated() + "\");";
            ContactAvatar.setCssCode("contact_avatar", cssCode);

            GuiLabel ContactName = new GuiLabel(contact.getName() + " " + contact.getLastname());
            ContactName.setCssId("contact_name");
            ContactName.addClickListener((x,y,bu) -> {
                //Minecraft.getMinecraft().displayGuiScreen(new ViewContact().getGuiScreen());
            });

            contactPanel.add(ContactAvatar);
            contactPanel.add(ContactName);
            contacts_list.add(contactPanel);
        }

        add(contacts_list);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/contacts.css"));
    }

    @Override
    public boolean needsCssReload() {
        return SPhone.DEV_MOD;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

}
