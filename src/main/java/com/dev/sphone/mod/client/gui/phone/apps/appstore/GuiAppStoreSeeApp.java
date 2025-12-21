package com.dev.sphone.mod.client.gui.phone.apps.appstore;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.PacketManageApp;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@AppDetails(type = AppType.SYSTEM)
public class GuiAppStoreSeeApp extends GuiBase {

    AppManager.App app;

    public GuiAppStoreSeeApp(GuiScreen parent, AppManager.App app) {
        super(parent);
        this.app = app;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(this.getRoot());

        GuiLabel appLabel = new GuiLabel("APP STORE");
        appLabel.setCssClass("appstore_title");

        GuiPanel appListPanel = new GuiPanel();
        appListPanel.setCssClass("see_app_list");
        appListPanel.setLayout(new GridLayout(200, -1, 5, GridLayout.GridDirection.VERTICAL, 1));

        System.out.println(app);

        try {
            GuiLabel appTitle = new GuiLabel(app.getName());
            appTitle.setCssClass("app_title");
            appListPanel.add(appTitle);

            GuiPanel appIconPanel = new GuiPanel();
            appIconPanel.setCssClass("app_display_icon");
            // CORRIGIDO: setTexture usando Customizer
            appIconPanel.getStyle().getCustomizer().setTexture(new GuiTextureSprite(app.getIcon()));
            appListPanel.add(appIconPanel);

            GuiLabel version = new GuiLabel("Version: " + app.getAppVersion());
            version.setCssClass("app_version");
            appListPanel.add(version);

            GuiLabel appDescription = new GuiLabel(app.getDescription());
            appDescription.setCssClass("app_description_text");
            appListPanel.add(appDescription);

            GuiPanel buttonGetOrAlready = new GuiPanel();

            if(getDownloadedApps(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND)).contains(app.getUniqueName())) {
                // CORRIGIDO: setTexture usando Customizer
                buttonGetOrAlready.getStyle().getCustomizer().setTexture(
                    new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/appstore/already.png"))
                );
                GuiLabel uninstall = new GuiLabel(I18n.format("sphone.appstore.usesettings"));
                uninstall.setCssClass("uninstall");
                appListPanel.add(uninstall);
            } else {
                buttonGetOrAlready.getStyle().getCustomizer().setTexture(
                    new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/appstore/get.png"))
                );
                buttonGetOrAlready.addClickListener((mouseX, mouseY, mouseButton) -> {
                    SPhone.network.sendToServer(new PacketManageApp(PacketManageApp.Actions.INSTALL, app.getUniqueName()));
                    Minecraft.getMinecraft().displayGuiScreen(new GuiAppStoreHome(new GuiHome().getGuiScreen()).getGuiScreen());
                });
            }

            buttonGetOrAlready.setCssClass("app_button");
            appListPanel.add(buttonGetOrAlready);

        } catch (Exception e) {
            GuiLabel appTitle = new GuiLabel("App not found");
            appTitle.setCssClass("app_title");
            appListPanel.add(appTitle);
            e.printStackTrace();
        }

        getRoot().add(appListPanel);
        getRoot().add(appLabel);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/appstore.css"));
        return styles;
    }
}
