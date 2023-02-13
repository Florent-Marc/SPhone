package fr.sandji.sphone.client.phone.apps.settings;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RingsPage extends GuiFrame {

    public RingsPage() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        if (PhoneData.OpenOnLastApp) {
            PhoneData.LastApp = "RingsPage";
        }

        PhoneSettings phoneSettings = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
        phoneSettings.loadPhoneSettings();

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
            ACsGuiApi.asyncLoadThenShowGui("SettingsPage", SettingsPage::new);
        });
        add(phone_task_bar);

        GuiLabel app_title = new GuiLabel("Sonneries :");
        app_title.setCssId("app_title");
        add(app_title);

        GuiLabel phone_background_one = new GuiLabel("Sonnerie 1");
        if (PhoneData.phone_background == 1) {
            phone_background_one.setText(TextFormatting.RED + "Sonnerie 1");
        }
        phone_background_one.setCssId("phone_background_one");
        phone_background_one.addClickListener((x,y,bu) -> {
            PhoneSettings phoneBackground1 = new PhoneSettings(PhoneData.phone_number, 1, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
            phoneBackground1.savePhoneSettings(phoneBackground1);
            ACsGuiApi.asyncLoadThenShowGui("BackgroundsPage", RingsPage::new);
        });
        add(phone_background_one);

        GuiLabel phone_background_two = new GuiLabel("Sonnerie 2");
        if (PhoneData.phone_background == 2) {
            phone_background_two.setText(TextFormatting.RED + "Sonnerie 2");
        }
        phone_background_two.setCssId("phone_background_two");
        phone_background_two.addClickListener((x,y,bu) -> {
            PhoneSettings phoneBackground2 = new PhoneSettings(PhoneData.phone_number, 2, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
            phoneBackground2.savePhoneSettings(phoneBackground2);
            ACsGuiApi.asyncLoadThenShowGui("BackgroundsPage", RingsPage::new);
        });
        add(phone_background_two);

        GuiLabel phone_background_three = new GuiLabel("Sonnerie 3");
        if (PhoneData.phone_background == 3) {
            phone_background_three.setText(TextFormatting.RED + "Sonnerie 3");
        }
        phone_background_three.setCssId("phone_background_three");
        phone_background_three.addClickListener((x,y,bu) -> {
            PhoneSettings phoneBackground3 = new PhoneSettings(PhoneData.phone_number, 3, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
            phoneBackground3.savePhoneSettings(phoneBackground3);
            ACsGuiApi.asyncLoadThenShowGui("BackgroundsPage", RingsPage::new);
        });
        add(phone_background_three);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/backgrounds.css"));
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
