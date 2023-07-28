package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.utils.Utils;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GuiBase extends GuiFrame {

    private final GuiScreen parent;
    private GuiPanel Background;
    private GuiPanel root;

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
        if(ClientEventHandler.isCameraActive){
            Utils.leaveCamera(false);
        }
    }

    private void init(){
        this.removeAllChilds();
        this.flushComponentsQueue();
        this.flushRemovedComponents();
        style.setBackgroundColor(Color.TRANSLUCENT);


        setCssClass("home");
        GuiPanel Case = new GuiPanel();
        Case.setCssClass("case");
        Case.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/" + getSettings().getBackground() + ".png")));
        add(Case);

        Background = new GuiPanel();
        Background.setCssClass("background");
        Case.add(Background);

        root = new GuiPanel();
        root.setCssId("root");


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

        GuiPanel camera = new GuiPanel();
        camera.setCssClass("camera");
        Case.add(camera);

        GuiPanel HomeBar = new GuiPanel();
        HomeBar.setCssClass("home_bar");
        add(HomeBar);
        if (parent == null) {
            HomeBar.setVisible(false);

        } else {
            HomeBar.addClickListener((x,y,bu) -> {
                Minecraft.getMinecraft().displayGuiScreen(parent);
            });
        }

    }

    public GuiPanel getBackground() {
        return this.Background;
    }

    public GuiPanel getRoot() {
        return this.root;
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
    public boolean allowDebugInGui() {
        return SPhone.DEV_MOD;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

    public PhoneSettings getSettings() {
        PhoneSettings settings = new PhoneSettings("acsgui");
        ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
        if(stack != null) {
            if(stack.getItem() instanceof ItemPhone) {

                settings.deserializeNBT(Objects.requireNonNull(Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound()).getCompoundTag("settings"));
            } else {
                Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation("sphone.error.no_phone", new Object[0]));            }
        } else {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation("sphone.error.no_phone", new Object[0]));
        }
        return settings;
    }

}
