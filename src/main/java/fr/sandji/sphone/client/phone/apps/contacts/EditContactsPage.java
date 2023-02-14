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

public class EditContactsPage extends GuiFrame {

    public EditContactsPage(String name, String last_name, int phone_number, String notes) {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

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
            mc.displayGuiScreen(new ViewContactsPage(name, last_name, phone_number, notes).getGuiScreen());
        });
        add(phone_task_bar);

        GuiLabel contact_avatar = new GuiLabel("");
        contact_avatar.setCssId("contact_avatar");
        add(contact_avatar);

        GuiTextField edit_name = new GuiTextField();
        edit_name.setCssId("edit_name");
        edit_name.setText(name);
        edit_name.setMaxTextLength(13);
        add(edit_name);

        GuiTextField edit_last_name = new GuiTextField();
        edit_last_name.setCssId("edit_last_name");
        edit_last_name.setText(last_name);
        edit_last_name.setMaxTextLength(13);
        add(edit_last_name);

        GuiIntegerField edit_phone = new GuiIntegerField(0, 5559999);
        edit_phone.setValue(phone_number);
        edit_phone.setCssId("edit_phone");
        add(edit_phone);

        GuiTextArea edit_notes = new GuiTextArea();
        edit_notes.setCssId("edit_notes");
        edit_notes.setText(" " + notes);
        edit_notes.setEditable(true);
        add(edit_notes);

        GuiLabel contact_add = new GuiLabel("Sauvegarder");
        contact_add.setCssId("contact_add");
        contact_add.addClickListener((x,y,bu) -> {
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
