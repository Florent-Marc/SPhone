package fr.sandji.sphone.client.phone.apps.settings;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.client.phone.HomePage;
import fr.sandji.sphone.client.util.GuiUtils;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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

        ItemStack head = new ItemStack(mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem(), 1, mc.player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getMetadata());
        GuiUtils.drawItemImage(head, this.getScreenX() - 110, this.getScreenY() / 2 - 40, 0.0F);

        GuiLabel phone_number = new GuiLabel("Votre Numéro :");
        phone_number.setCssId("phone_number");
        phone_number.setText("Votre Numéro : " + PhoneSettings.phone_number);
        add(phone_number);

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
