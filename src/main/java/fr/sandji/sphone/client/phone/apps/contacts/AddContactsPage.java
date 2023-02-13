package fr.sandji.sphone.client.phone.apps.contacts;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiIntegerField;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextArea;
import fr.aym.acsguis.component.textarea.GuiTextField;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AddContactsPage extends GuiFrame {

    public AddContactsPage() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        ContactManager.loadContacts();

        GuiLabel phone_background = new GuiLabel("");
        phone_background.setCssId("phone_background_gray");
        add(phone_background);

        GuiLabel phone_case = new GuiLabel("");
        phone_case.setCssId("phone_case");
        add(phone_case);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();

        GuiLabel phone_clock = new GuiLabel(dateFormat.format(date));
        phone_clock.setCssId("phone_clock");
        add(phone_clock);

        GuiLabel phone_top_icons = new GuiLabel("");
        phone_top_icons.setCssId("phone_top_icons");
        add(phone_top_icons);

        GuiLabel phone_task_bar = new GuiLabel("");
        phone_task_bar.setCssId("phone_task_bar");
        phone_task_bar.addClickListener((x,y,bu) -> {
            ACsGuiApi.asyncLoadThenShowGui("ContactsPage", ContactsPage::new);
        });
        add(phone_task_bar);

        GuiLabel app_title = new GuiLabel("Nouveau Contact");
        app_title.setCssId("app_title");
        add(app_title);

        GuiTextField contact_name = new GuiTextField();
        contact_name.setCssId("contact_name");
        contact_name.setHintText("Prénom");
        contact_name.setMaxTextLength(13);
        add(contact_name);

        GuiTextField contact_lastname = new GuiTextField();
        contact_lastname.setCssId("contact_lastname");
        contact_lastname.setHintText("Nom");
        contact_lastname.setMaxTextLength(13);
        add(contact_lastname);

        GuiIntegerField contact_number = new GuiIntegerField(0, 5559999);
        contact_number.setCssId("contact_number");
        contact_number.setHintText("Numéro");
        contact_number.setText("");
        add(contact_number);

        GuiTextArea contact_notes = new GuiTextArea();
        contact_notes.setCssId("contact_notes");
        contact_notes.setHintText("Notes");
        add(contact_notes);

        GuiLabel contact_add = new GuiLabel("Sauvegarder");
        contact_add.setCssId("contact_add");
        contact_add.addClickListener((x,y,bu) -> {
            ContactManager.loadContacts();
            ContactManager.Contact contact = new ContactManager.Contact(contact_number.getValue(), contact_name.getText(), contact_lastname.getText(), contact_notes.getText());
            ContactManager.addContact(contact);
            ContactManager.saveContacts();
            ACsGuiApi.asyncLoadThenShowGui("ContactsPage", ContactsPage::new);
        });
        add(contact_add);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/contacts.css"));
    }

    @Override
    public boolean needsCssReload() {
        return true;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

}
