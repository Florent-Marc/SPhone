
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiHome extends GuiInit {

    public GuiHome() {
        super();
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");


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

        GuiPanel AppContact = new GuiPanel();
        AppContact.setCssClass("app_contact");
        AppContact.addClickListener((x, y, bu) -> {
            List<Contact> test = new ArrayList<Contact>();
            test.add(new Contact("Markus", "Kane", 14256, "Super Mec", "0hSandji"));
            test.add(new Contact("MK", "Kane", 14256, "Super Mec", "MK_16"));
            test.add(new Contact("Paris", "Kane", 14256, "Super Mec", "Zoutesou"));

            Minecraft.getMinecraft().displayGuiScreen(new GuiContactsList(test).getGuiScreen());
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