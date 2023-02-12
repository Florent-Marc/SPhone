package fr.sandji.sphone.client.phone.apps.settings;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.client.phone.HomePage;
import fr.sandji.sphone.client.util.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
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

        PhoneSettings phoneSettings = new PhoneSettings(PhoneData.phone_number, PhoneData.phone_background, PhoneData.phone_ring, PhoneData.OpenOnLastApp);
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

        GuiLabel phone_number = new GuiLabel("Votre Numéro :");
        phone_number.setCssId("phone_number");
        phone_number.setText("Votre Numéro : " + PhoneData.phone_number);
        add(phone_number);

        GuiLabel phone_background_one = new GuiLabel("Fond D'écran 1");
        if (PhoneData.phone_background == 1) {
            phone_background_one.setText(TextFormatting.RED + "Fond D'écran 1");
        }
        phone_background_one.setCssId("phone_background_one");
        phone_background_one.addClickListener((x,y,bu) -> {
            PhoneSettings phoneBackground1 = new PhoneSettings(PhoneData.phone_number, 1, PhoneData.phone_ring, PhoneData.OpenOnLastApp);
            phoneBackground1.savePhoneSettings(phoneBackground1);
            ACsGuiApi.asyncLoadThenShowGui("SettingsPage", SettingsPage::new);
        });
        add(phone_background_one);

        GuiLabel phone_background_two = new GuiLabel("Fond D'écran 2");
        if (PhoneData.phone_background == 2) {
            phone_background_two.setText(TextFormatting.RED + "Fond D'écran 2");
        }
        phone_background_two.setCssId("phone_background_two");
        phone_background_two.addClickListener((x,y,bu) -> {
            PhoneSettings phoneBackground2 = new PhoneSettings(PhoneData.phone_number, 2, PhoneData.phone_ring, PhoneData.OpenOnLastApp);
            phoneBackground2.savePhoneSettings(phoneBackground2);
            ACsGuiApi.asyncLoadThenShowGui("SettingsPage", SettingsPage::new);
        });
        add(phone_background_two);

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void drawForeground(int mouseX, int mouseY, float partialTicks) {
        super.drawForeground(mouseX, mouseY, partialTicks);
        ItemStack head = new ItemStack(mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem(), 1, mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getMetadata());

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.getScreenX() - 110, this.getScreenY() / 2 - 40, 300.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(195.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(0.0F % 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(100.0F, 100.0F, 100.0F);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(head, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
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
