
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.ui.phone;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.ui.phone.apps.contacts.Contacts;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Home extends GuiFrame {

    public Home() {
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

        GuiLabel Apps = new GuiLabel("");
        Apps.setCssId("apps");
        add(Apps);

        GuiLabel AppCall = new GuiLabel("");
        AppCall.setCssId("app_call");
        add(AppCall);

        GuiLabel AppNotes = new GuiLabel("");
        AppNotes.setCssId("app_notes");
        add(AppNotes);

        GuiLabel AppGalery = new GuiLabel("");
        AppGalery.setCssId("app_gallery");
        add(AppGalery);

        GuiLabel AppMessage = new GuiLabel("");
        AppMessage.setCssId("app_message");
        add(AppMessage);

        GuiLabel AppMessageText = new GuiLabel("");
        AppMessageText.setCssId("app_text");
        AppMessageText.setCssCode("app_text", "left: 1623px;" + "top: 655px;");
        add(AppMessageText);

        GuiLabel AppInstagram = new GuiLabel("");
        AppInstagram.setCssId("app_instagram");
        add(AppInstagram);

        GuiLabel AppInstagramText = new GuiLabel("");
        AppInstagramText.setCssId("app_text");
        AppInstagramText.setCssCode("app_text", "left: 1700px;" + "top: 655px;");
        add(AppInstagramText);

        GuiLabel AppSettings = new GuiLabel("");
        AppSettings.setCssId("app_settings");
        add(AppSettings);

        GuiLabel AppSettingsText = new GuiLabel("");
        AppSettingsText.setCssId("app_settings");
        AppSettingsText.setCssCode("app_text", "left: 1777;" + "top: 655px;");
        add(AppSettingsText);

        GuiLabel AppMap = new GuiLabel("");
        AppMap.setCssId("app_map");
        add(AppMap);

        GuiLabel AppContact = new GuiLabel("");
        AppContact.setCssId("app_contact");
        AppContact.addClickListener((x,y,bu) -> {
            //Minecraft.getMinecraft().displayGuiScreen(new Contacts().getGuiScreen());
        });
        add(AppContact);
    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/home.css"));
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
