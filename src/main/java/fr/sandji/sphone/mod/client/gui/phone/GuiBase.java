
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GuiBase extends GuiFrame {

    private final GuiScreen parent;
    private GuiPanel Background;

    public GuiBase(GuiScreen parent) {
        super(new GuiScaler.AdjustFullScreen());
        this.parent = parent;
        init();
    }

    public GuiBase() {
        super(new GuiScaler.AdjustFullScreen());
        this.parent = null;
        init();
    }

    @Override
    public void resize(GuiFrame.APIGuiScreen gui, int screenWidth, int screenHeight) {
        super.resize(gui, screenWidth, screenHeight);
        this.GuiInit();
    }

    public void GuiInit(){
        init();
    }

    private void init(){
        this.removeAllChilds();
        this.flushComponentsQueue();
        this.flushRemovedComponents();
        style.setBackgroundColor(Color.TRANSLUCENT);

        setCssClass("home");
        GuiPanel Case = new GuiPanel();
        Case.setCssClass("case");
        add(Case);

        Background = new GuiPanel();
        Background.setCssClass("background");
        Case.add(Background);

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
        add(HomeBar);
        if (parent == null) {
            HomeBar.setVisible(false);

        } else {
            HomeBar.addClickListener((x,y,bu) -> {
                Minecraft.getMinecraft().displayGuiScreen(parent);
                //Minecraft.getMinecraft().displayGuiScreen(new GuiHome().guiScreen);

            });
        }

    }

    public GuiPanel getBackground() {
        return this.Background;
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/base.css"));
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
