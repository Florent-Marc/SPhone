package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.packets.server.PacketManageApp;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiAppsSettings extends GuiBase {

    public GuiAppsSettings(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        add(getRoot());

        GuiPanel app_container = new GuiPanel();
        app_container.setCssClass("app_container");

        GuiLabel appTitle = new GuiLabel("Paramètres");
        appTitle.setCssId("app_title");

        GuiScrollPane settings_list = new GuiScrollPane();
        settings_list.setCssClass("settings_list");
        settings_list.setLayout(new GridLayout(-1, 80, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        GuiLabel customisation_desc = new GuiLabel("Applications installées");
        customisation_desc.setCssClass("settings_element_desc_small");

        getDownloadedApps(Minecraft.getMinecraft().player.getHeldItemMainhand()).forEach(app -> {
            AppManager.App app1 = AppManager.getApps().stream().filter(app2 -> app2.getUniqueName().equals(app)).findFirst().orElse(null);
            if (app1 == null) return; // Evita NPE se app não for encontrado
            GuiPanel customisation = getGuiElement(app1.getIcon(), app1.getName());

            customisation.addClickListener((mouseX, mouseY, mouseButton) -> {
                SPhone.network.sendToServer(new PacketManageApp(PacketManageApp.Actions.UNINSTALL, app));
                settings_list.remove(customisation);
            });

            settings_list.add(customisation);
        });

        app_container.add(settings_list);
        app_container.add(appTitle);
        app_container.add(customisation_desc);

        getRoot().add(app_container);
    }

    public GuiPanel getGuiElement(ResourceLocation iconLoc, String name) {
        GuiPanel customisation = new GuiPanel();
        customisation.setCssClass("settings_element");

        GuiPanel customisation_icon = new GuiPanel();
        customisation_icon.setCssClass("settings_element_icon");
        // CORRIGIDO: use o Customizer para setar a textura:
        customisation_icon.getStyle().getCustomizer().setTexture(new GuiTextureSprite(iconLoc));
        customisation.add(customisation_icon);

        GuiLabel customisation_name = new GuiLabel(name);
        customisation_name.setCssClass("settings_element_name");
        customisation.add(customisation_name);

        return customisation;
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/settings.css"));
        return styles;
    }
}
