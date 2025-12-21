package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.GuiComponent;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;


import java.util.*;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.List;

public class GuiBase extends GuiFrame {

    private final GuiScreen parent;
    private GuiPanel Background;
    private GuiPanel root;
    private boolean isInfosHidden = false;

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

    public void GuiInit() {
        init();
        if (ClientEventHandler.isCameraActive) {
            UtilsClient.leaveCamera(false);
        }
    }

    private void init() {
    this.removeAllChildren();
    this.flushComponentsQueue();
    this.flushRemovedComponents();

    // NOVO: setBackgroundColor() via Customizer
    getStyle().getCustomizer().setBackgroundColor(0x00000000); // transparente

    setCssClass("home");
    GuiPanel Case = new GuiPanel();
    Case.setCssClass("case");

    // NOVO: setTexture() via Customizer
    Case.getStyle().getCustomizer().setTexture(
        new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/" + getSettings().getBackground() + ".png"))
    );
    add(Case);

    if (getSettings().getBackground().equals("")) {
        Case.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/acsgui.png"))
        );
    }

    Background = new GuiPanel();
    Background.setCssClass("background");
    Case.add(Background);

    root = new GuiPanel();
    root.setCssId("root");

    if (!isInfosHidden) {
        GuiLabel TopClock = new GuiLabel("");
        TopClock.setCssId("top_clock");
        String dateS = I18n.format("sphone.timeformat");
        // if not set
        if (dateS.equals("sphone.timeformat")) {
            dateS = "HH:mm";
        }
        String finalDateS = dateS;
        TopClock.addTickListener(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat(finalDateS);
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
            // NOVO: setVisible(false) via Customizer
            HomeBar.getStyle().getCustomizer().setVisible(false);
        } else {
            HomeBar.addClickListener((x, y, bu) -> {
                Minecraft.getMinecraft().displayGuiScreen(parent);
            });
        }
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

    public void setInfosHidden(boolean infosHidden) {
        isInfosHidden = infosHidden;
    }

    public static List<String> getDownloadedApps(ItemStack stack) {
        if(!(stack.getItem() instanceof ItemPhone)) {
            return new ArrayList<>();
        }
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            return new ArrayList<>();
        }
        NBTTagCompound apps = tag.getCompoundTag("apps");
        return new ArrayList<>(apps.getKeySet());
    }

    public PhoneSettings getSettings() {
        PhoneSettings settings = new PhoneSettings("acsgui");
        ItemStack stack = Minecraft.getMinecraft().player.getHeldItemMainhand();
        if (stack.getItem() instanceof ItemPhone) {
            settings.deserializeNBT(Objects.requireNonNull(Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound()).getCompoundTag("settings"));
        }
        return settings;
    }
}
