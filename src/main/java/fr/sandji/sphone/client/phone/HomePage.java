package fr.sandji.sphone.client.phone;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomePage extends GuiFrame {

    public HomePage() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        GuiLabel phone_background = new GuiLabel("");
        phone_background.setCssId("phone_background_one");
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

        GuiLabel phone_tool_app = new GuiLabel("");
        phone_tool_app.setCssId("phone_tool_apps");
        add(phone_tool_app);

        GuiLabel phone_app_call = new GuiLabel("");
        phone_app_call.setCssId("phone_app_call");
        add(phone_app_call);

        GuiLabel phone_app_messages = new GuiLabel("");
        phone_app_messages.setCssId("phone_app_messages");
        add(phone_app_messages);

        GuiLabel phone_app_notes = new GuiLabel("");
        phone_app_notes.setCssId("phone_app_notes");
        add(phone_app_notes);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/home.css"));
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
