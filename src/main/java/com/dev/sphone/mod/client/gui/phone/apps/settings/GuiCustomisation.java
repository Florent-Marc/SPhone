/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.items.ItemPhone;
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

import java.util.ArrayList;
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
        backgrounds.setCssClass("backgrounds");
        backgrounds.setLayout(new GridLayout(new Size.SizeValue(50, GuiConstants.ENUM_SIZE.ABSOLUTE), new Size.SizeValue(150, GuiConstants.ENUM_SIZE.ABSOLUTE), new Size.SizeValue(20, GuiConstants.ENUM_SIZE.ABSOLUTE), GridLayout.GridDirection.VERTICAL, 2));


        GuiPanel acsBackground = new GuiPanel();
        acsBackground.setCssClass("background");
        acsBackground.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/acsgui.png"), 0, 0, 0, 0));

        GuiLabel acsBackgroundLabel = new GuiLabel("ACS-GUI");
        acsBackgroundLabel.setCssClass("background_label" + (settings.getBackground().equals("acsgui") ? "_selected" : ""));
        backgrounds.add(acsBackgroundLabel);

        acsBackground.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("acsgui");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });

        backgrounds.add(acsBackground);


        GuiPanel deauvilleBackground = new GuiPanel();
        deauvilleBackground.setCssClass("background");
        deauvilleBackground.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/deauville.png"), 0, 0, 0,0));
        GuiLabel deauvilleBackgroundLabel = new GuiLabel("Deauville");
        deauvilleBackgroundLabel.setCssClass("background_label" + (settings.getBackground().equals("deauville") ? "_selected" : ""));
        backgrounds.add(deauvilleBackgroundLabel);
        deauvilleBackground.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("deauville");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });
        backgrounds.add(deauvilleBackground);


        GuiPanel stmichel = new GuiPanel();
        stmichel.setCssClass("background");
        stmichel.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/stmichel.png"), 0, 0, 0,0));
        GuiLabel stmichelLabel = new GuiLabel("St Michel");
        stmichelLabel.setCssClass("background_label" + (settings.getBackground().equals("stmichel") ? "_selected" : ""));
        backgrounds.add(stmichelLabel);
        stmichel.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("stmichel");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });
        backgrounds.add(stmichel);

        GuiPanel playa = new GuiPanel();
        playa.setCssClass("background");
        playa.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/playa.png"), 0, 0, 0,0));
        GuiLabel playaLabel = new GuiLabel("Playa");
        playaLabel.setCssClass("background_label" + (settings.getBackground().equals("playa") ? "_selected" : ""));
        backgrounds.add(playaLabel);
        playa.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("playa");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });
        backgrounds.add(playa);

        GuiPanel illumination = new GuiPanel();
        illumination.setCssClass("background");
        illumination.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/iluminacion.png"), 0, 0, 0,0));
        GuiLabel illuminationLabel = new GuiLabel("Illumination");
        illuminationLabel.setCssClass("background_label" + (settings.getBackground().equals("iluminacion") ? "_selected" : ""));
        backgrounds.add(illuminationLabel);
        illumination.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("iluminacion");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });
        backgrounds.add(illumination);

        GuiPanel oscuridad = new GuiPanel();
        oscuridad.setCssClass("background");
        oscuridad.getStyle().setTexture(new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/oscuridad.png"), 0, 0, 0,0));
        GuiLabel oscuridadLabel=  new GuiLabel("Oscuridad");
        oscuridadLabel.setCssClass("background_label" + (settings.getBackground().equals("oscuridad") ? "_selected" : ""));
        backgrounds.add(oscuridadLabel);
        oscuridad.addClickListener((mouseX, mouseY, mouseButton) -> {
            settings.setBackground("oscuridad");
            Minecraft.getMinecraft().player.getHeldItemMainhand().getTagCompound().setTag("settings", settings.serializeNBT());
            Minecraft.getMinecraft().displayGuiScreen(getRoot().getGui());
        });
        backgrounds.add(oscuridad);

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
