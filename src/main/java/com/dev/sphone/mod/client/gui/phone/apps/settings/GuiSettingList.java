
package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
@AppDetails(type = AppType.DEFAULT)
public class GuiSettingList extends GuiBase {

    public GuiSettingList(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        add(getRoot());

        System.out.println("test");

        GuiPanel app_container = new GuiPanel();
        app_container.setCssClass("app_container");

        GuiLabel appTitle = new GuiLabel("Paramètres");
        appTitle.setCssId("app_title");

        GuiScrollPane settings_list = new GuiScrollPane();
        settings_list.setCssClass("settings_list");
        settings_list.setLayout(new GridLayout(-1, 80, 1, GridLayout.GridDirection.HORIZONTAL, 1));

        GuiLabel customisation_desc = new GuiLabel("Préfénrences");
        customisation_desc.setCssClass("settings_element_desc");
        settings_list.add(customisation_desc);

        settings_list.add(getGuiElement(new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/settings/custom.png"), "Customisation")).addClickListener(
                (mouseX, mouseY, mouseButton) -> {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiCustomisation(this.getGuiScreen()).getGuiScreen());
                }
        );
        settings_list.add(getGuiElement(new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/settings/general.png"), "Général"));

        GuiLabel infos_desc = new GuiLabel("Appareil");
        infos_desc.setCssClass("settings_element_desc");
        settings_list.add(customisation_desc);


        settings_list.add(infos_desc);

        app_container.add(settings_list);
        app_container.add(appTitle);


        getRoot().add(app_container);
    }


    public GuiPanel getGuiElement(ResourceLocation iconLoc, String name) {
        GuiPanel customisation = new GuiPanel();
        customisation.setCssClass("settings_element");

        GuiPanel customisation_icon = new GuiPanel();
        customisation_icon.setCssClass("settings_element_icon");
        customisation_icon.getStyle().setTexture(new GuiTextureSprite(iconLoc, 0, 0, 0,0));

        customisation.add(customisation_icon);


        GuiLabel customisation_name = new GuiLabel(name);
        customisation_name.setCssClass("settings_element_name");
        customisation.add(customisation_name);

        return customisation;
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/settings.css"));
        return styles;
    }

}
