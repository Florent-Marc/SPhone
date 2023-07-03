
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GuiInit extends GuiFrame {

    private GuiScreen parent;

    public GuiInit(GuiScreen parent) {
        super(new GuiScaler.AdjustFullScreen());
        this.parent = parent;
        init();
    }

    public GuiInit() {
        super(new GuiScaler.AdjustFullScreen());
        init();
    }

    private void init(){
        GuiPanel Background = new GuiPanel();
        Background.setCssClass("background");
        add(Background);

        GuiPanel Case = new GuiPanel();
        Case.setCssClass("case");
        add(Case);

        GuiLabel TopClock = new GuiLabel("");
        TopClock.setCssId("top_clock");
        TopClock.addTickListener(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            TopClock.setText(dateFormat.format(date));
        });
        add(TopClock);

        GuiPanel TopIcons = new GuiPanel();
        TopIcons.setCssClass("top_icons");
        add(TopIcons);

        GuiPanel HomeBar = new GuiPanel();
        HomeBar.setCssClass("home_bar");
        HomeBar.addClickListener((x,y,bu) -> {
            if (parent != null) {
                Minecraft.getMinecraft().displayGuiScreen(parent);
            }
        });
        add(HomeBar);
    }




    @Override
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
