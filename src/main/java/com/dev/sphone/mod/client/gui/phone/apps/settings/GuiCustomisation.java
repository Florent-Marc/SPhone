/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.utils.Utils;
import fr.aym.acsguis.component.GuiComponent;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GuiCustomisation extends GuiBase {

    public GuiCustomisation(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        add(getRoot());
        PhoneSettings settings = getSettings();


        GuiPanel app_container = new GuiPanel();
        app_container.setCssClass("app_container");

        GuiLabel appTitle = new GuiLabel("Paramètres");
        appTitle.setCssId("app_title");

        GuiScrollPane backgrounds = new GuiScrollPane();
        backgrounds.setCssId("scroll");
        backgrounds.setLayout(new GridLayout(new Size.SizeValue(50, GuiConstants.ENUM_SIZE.ABSOLUTE), new Size.SizeValue(150, GuiConstants.ENUM_SIZE.ABSOLUTE), new Size.SizeValue(20, GuiConstants.ENUM_SIZE.ABSOLUTE), GridLayout.GridDirection.VERTICAL, 2));

        setEnableDebugPanel(true);



        String[] backgroundsConfig = Utils.getBackgrounds();

        for (String s : backgroundsConfig) {
            String id = s.split(":")[0];
            String name = s.split(":")[1];

            GuiPanel backgroundpanel = new GuiPanel();
            backgroundpanel.setCssClass("background");
            backgroundpanel.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/" + id + ".png"), 0, 0, 0,0));
            GuiLabel oscuridadLabel=  new GuiLabel(name);
            oscuridadLabel.setCssClass("background_label" + (settings.getBackground().equals(id) ? "_selected" : ""));
            backgrounds.add(oscuridadLabel);
            backgroundpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                settings.setBackground(id);
                Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
                Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
            });
            backgrounds.add(backgroundpanel);

        }

        app_container.add(backgrounds);
        app_container.add(appTitle);
        getRoot().add(app_container);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/settings.css"));
        return styles;
    }

}
