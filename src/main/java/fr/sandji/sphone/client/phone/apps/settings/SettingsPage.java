package fr.sandji.sphone.client.phone.apps.settings;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.entity.GuiEntityRender;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.client.phone.HomePage;
import fr.sandji.sphone.client.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SettingsPage extends GuiFrame {

    public SettingsPage() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        if (PhoneData.OpenOnLastApp) {
            PhoneData.LastApp = "SettingsPage";
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
            ACsGuiApi.asyncLoadThenShowGui("HomePage", HomePage::new);
        });
        add(phone_task_bar);

        GuiLabel app_title = new GuiLabel("Paramètres :");
        app_title.setCssId("app_title");
        add(app_title);

        GuiLabel player_head_back = new GuiLabel("");
        player_head_back.setCssId("player_head_back");
        add(player_head_back);

        GuiEntityRender player_head = new GuiEntityRender(mc.player);
        player_head.setCssId("player_head");
        add(player_head);

        GuiLabel phone_number = new GuiLabel("Votre Numéro :");
        phone_number.setCssId("phone_number");
        phone_number.setText("Votre Numéro : " + PhoneData.phone_number);
        add(phone_number);

        GuiLabel phone_background_page = new GuiLabel("Fond d'Écran ");
        phone_background_page.setCssId("phone_background_page");
        phone_background_page.addClickListener((x,y,bu) -> {
            ACsGuiApi.asyncLoadThenShowGui("BackgroundsPage", BackgroundsPage::new);
        });
        add(phone_background_page);

        GuiLabel phone_ring_page = new GuiLabel("Sonnerie ");
        phone_ring_page.setCssId("phone_ring_page");
        phone_ring_page.addClickListener((x,y,bu) -> {
            ACsGuiApi.asyncLoadThenShowGui("RingsPage", RingsPage::new);
        });
        add(phone_ring_page);

        GuiLabel phone_open_last_app_back = new GuiLabel(" Save Last App");
        phone_open_last_app_back.setCssId("phone_open_last_app");
        add(phone_open_last_app_back);

        GuiButton phone_open_last_app = new GuiButton();
        if (PhoneData.OpenOnLastApp) {
            phone_open_last_app.setCssClass("switch-button-on");
        } else if (!PhoneData.OpenOnLastApp) {
            phone_open_last_app.setCssClass("switch-button-off");
        }
        phone_open_last_app.addClickListener((x,y,bu) -> {
            if (PhoneData.OpenOnLastApp) {
                PhoneSettings phoneSettings1 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, false, PhoneData.phone_silence_mod);
                phoneSettings1.savePhoneSettings(phoneSettings1);
                PhoneSettings phoneSettings3 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
                phoneSettings3.loadPhoneSettings();
                phone_open_last_app.setCssClass("switch-button-off");
            }
            else if (!PhoneData.OpenOnLastApp) {
                PhoneSettings phoneSettings2 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, true, PhoneData.phone_silence_mod);
                phoneSettings2.savePhoneSettings(phoneSettings2);
                PhoneSettings phoneSettings4 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
                phoneSettings4.loadPhoneSettings();
                phone_open_last_app.setCssClass("switch-button-on");
            }
        });
        add(phone_open_last_app);

        GuiLabel phone_silence_mod_back = new GuiLabel(" Mode Silencieux");
        phone_silence_mod_back.setCssId("phone_silence_mod_back");
        add(phone_silence_mod_back);

        GuiButton phone_silence_mod = new GuiButton();
        if (PhoneData.phone_silence_mod) {
            phone_silence_mod.setCssClass("switch-button-on-2");
        } else if (!PhoneData.phone_silence_mod) {
            phone_silence_mod.setCssClass("switch-button-off-2");
        }
        phone_silence_mod.addClickListener((x,y,bu) -> {
            if (PhoneData.phone_silence_mod) {
                PhoneSettings phoneSettings1 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, false);
                phoneSettings1.savePhoneSettings(phoneSettings1);
                PhoneSettings phoneSettings3 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
                phoneSettings3.loadPhoneSettings();
                phone_silence_mod.setCssClass("switch-button-off-2");
            }
            else if (!PhoneData.phone_silence_mod) {
                PhoneSettings phoneSettings2 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, true);
                phoneSettings2.savePhoneSettings(phoneSettings2);
                PhoneSettings phoneSettings4 = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp, PhoneData.phone_silence_mod);
                phoneSettings4.loadPhoneSettings();
                phone_silence_mod.setCssClass("switch-button-on-2");
            }
        });
        add(phone_silence_mod);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/settings.css"));
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
