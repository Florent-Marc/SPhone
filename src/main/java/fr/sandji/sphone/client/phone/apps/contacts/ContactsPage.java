package fr.sandji.sphone.client.phone.apps.contacts;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.client.phone.HomePage;
import fr.sandji.sphone.client.phone.apps.settings.SettingsPage;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ContactsPage extends GuiFrame {

    public ContactsPage() {
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
            ACsGuiApi.asyncLoadThenShowGui("HomePage", HomePage::new);
        });
        add(phone_task_bar);

        GuiLabel app_title = new GuiLabel("Contacts");
        app_title.setCssId("app_title");
        add(app_title);

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
