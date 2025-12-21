package com.dev.sphone.mod.client.gui.phone.apps.appstore;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.utils.CircleBackground;
import fr.aym.acsguis.utils.GuiTextureSprite;
import fr.aym.acsguis.utils.IGuiTexture;
import fr.aym.acsguis.utils.ComponentRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AppDetails(type = AppType.SYSTEM)
public class GuiAppStoreHome extends GuiBase {

    public GuiAppStoreHome(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(this.getRoot());

        GuiLabel appLabel = new GuiLabel("APP STORE");
        appLabel.setCssClass("appstore_title");

        GuiScrollPane appListPanel = new GuiScrollPane();
        appListPanel.setCssClass("app_list");
        appListPanel.setLayout(new GridLayout(200, -1, 5, GridLayout.GridDirection.VERTICAL, 1));

        List<AppManager.App> apps = AppManager.getApps().stream().filter(app -> !app.isDefaultOnPhone()).collect(Collectors.toList());
        apps.sort((o1, o2) -> Math.random() < 0.5 ? -1 : 1);

        for (AppManager.App app : apps.subList(0, Math.min(apps.size(), 3))) {
            int bgColor = 0;
            try {
                InputStream icon = Minecraft.getMinecraft().getResourceManager().getResource(app.getIcon()).getInputStream();
                BufferedImage image = ImageIO.read(icon);
                BufferedImage resized = new BufferedImage(50, 50, image.getType());
                resized.createGraphics().drawImage(image, 0, 0, 50, 50, null);

                bgColor = UtilsClient.getAverageColor(resized);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            IGuiTexture texture = null;
            ResourceLocation bannerLoc = new ResourceLocation("sphone:textures/appstore/" + app.getName().toLowerCase().replaceAll(" ", "") + ".png");
            try {
                Minecraft.getMinecraft().getResourceManager().getResource(bannerLoc).getInputStream();
                texture = new GuiTextureSprite(bannerLoc);
            } catch (IOException e) {
                System.out.println("No banner for " + app.getName() + " found.");
            }

            int finalBgColor = bgColor;
            IGuiTexture finalTexture = texture;
            GuiPanel appPanel = new GuiPanel() {
                @Override
                public void drawBackground(int mouseX, int mouseY, float partialTicks, ComponentRenderContext context) {
                    CircleBackground.renderBackground(50, (float) this.getScreenX(), (float) this.getScreenY(), (float) (this.getScreenX() + this.getWidth()), (float) (this.getScreenY() + this.getHeight()), finalBgColor);

                    IGuiTexture renderTexture = finalTexture;
                    if (renderTexture != null) {
                        GlStateManager.enableBlend();
                        renderTexture.drawSprite(this.getScreenX(), this.getScreenY(), this.getWidth(), this.getHeight());
                        GlStateManager.disableBlend();
                    }
                }
            };

            GuiPanel shadowPanel = new GuiPanel();
            shadowPanel.setCssClass("app_list_item_shadow");
            shadowPanel.getStyle().getCustomizer().setTexture(
                new GuiTextureSprite(new ResourceLocation("sphone:textures/appstore/shadow.png"))
            );

            appPanel.setCssClass("app_list_item");

            GuiLabel appName = new GuiLabel(app.getName());
            appName.setCssClass("app_list_item_name");

            GuiPanel appIcon = new GuiPanel();
            appIcon.setCssClass("app_list_item_icon");
            appIcon.getStyle().getCustomizer().setTexture(
                new GuiTextureSprite(app.getIcon())
            );

            appPanel.add(appIcon);
            appPanel.add(appName);

            shadowPanel.add(appPanel);
            appListPanel.add(shadowPanel);
        }
        appListPanel.getxSlider().getStyle().getCustomizer().setVisible(true);

        GuiPanel divider = new GuiPanel();
        divider.setCssClass("divider");
        getRoot().add(divider);

        GuiPanel bottomBar = new GuiPanel();
        bottomBar.setCssClass("bottom_bar");
        bottomBar.setLayout(new GridLayout(80, -1, 5, GridLayout.GridDirection.VERTICAL, 1));

        GuiPanel news = new GuiPanel() {
            @Override
            public void drawTexturedBackground(int mouseX, int mouseY, float partialTicks) {
                super.drawTexturedBackground(mouseX, mouseY, partialTicks);
            }
        };
        news.setCssClass("icon");
        news.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/appstore/news.png"))
        );

        GuiPanel games = new GuiPanel();
        games.setCssClass("icon");
        games.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/appstore/games.png"))
        );

        GuiPanel search = new GuiPanel();
        search.setCssClass("icon");
        search.getStyle().getCustomizer().setTexture(
            new GuiTextureSprite(new ResourceLocation("sphone:textures/ui/icons/appstore/search.png"))
        );

        bottomBar.add(news);
        bottomBar.add(games);
        bottomBar.add(search);
        getBackground().add(bottomBar);

        GuiLabel recommendedLabel = new GuiLabel(I18n.format("sphone.appstore.recommended"));
        recommendedLabel.setCssClass("appstore_recommended");

        GuiScrollPane recommendedPanel = new GuiScrollPane();
        recommendedPanel.setCssClass("recommanded_app_list");
        List<AppManager.App> recommendedApps = AppManager.getApps().stream().filter(app -> !app.isDefaultOnPhone()).collect(Collectors.toList());
        recommendedApps.sort((o1, o2) -> Math.random() < 0.5 ? -1 : 1);

        recommendedPanel.setLayout(new GridLayout(-1, 50, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        for (AppManager.App app : recommendedApps.subList(0, Math.min(recommendedApps.size(), 3))) {
            GuiPanel appPanel = new GuiPanel();
            appPanel.setCssClass("recommanded_app_list_item");

            GuiLabel appName = new GuiLabel(app.getName());
            appName.setCssClass("recommanded_app_list_item_name");

            GuiPanel appIcon = new GuiPanel();
            appIcon.setCssClass("recommanded_app_list_item_icon");
            appIcon.getStyle().getCustomizer().setTexture(
                new GuiTextureSprite(app.getIcon())
            );

            GuiPanel getButton = new GuiPanel();
            getButton.setCssClass("recommanded_app_list_item_get");
            getButton.addClickListener((mouseX, mouseY, mouseButton) -> {
                if (app.getRunnable() != null) {
                    app.getRunnable().run();
                }
                if (app.getGui() != null) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiAppStoreSeeApp(this.getGuiScreen(), app).getGuiScreen());
                }
            });

            appPanel.add(getButton);
            appPanel.add(appIcon);
            appPanel.add(appName);

            recommendedPanel.add(appPanel);
        }

        recommendedPanel.getySlider().getStyle().getCustomizer().setVisible(true);

        getRoot().add(recommendedLabel);
        getRoot().add(recommendedPanel);
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
