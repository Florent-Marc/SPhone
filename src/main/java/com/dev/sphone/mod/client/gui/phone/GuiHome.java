package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.apps.DevGui;
import com.dev.sphone.mod.client.gui.phone.apps.contacts.GuiNewContact;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.utils.GuiTextureSprite;
import com.dev.sphone.SPhone;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiHome extends GuiBase {

    public static boolean DEVMODE_LOCAL = false;

    public GuiHome() {
        super();
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        GuiNewContact.contactInCreation = null; // clear contact in creation because user went back to home.
        AppManager.reloadApps(this.getGuiScreen());

        GuiScrollPane appListPanel = new GuiScrollPane();
        appListPanel.setCssClass("app_list");
        appListPanel.setLayout(
                new GridLayout(
                        new Size.SizeValue(50, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(50, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(0.06f, GuiConstants.ENUM_SIZE.RELATIVE),
                        GridLayout.GridDirection.HORIZONTAL,
                        4
                )
        );

        GuiPanel appBottomPanel = new GuiPanel();
        appBottomPanel.setCssClass("app_list_bottom");

        GuiPanel decoBottom = new GuiPanel();
        decoBottom.setCssClass("app_list_bottom_deco");
        getBackground().add(decoBottom);

        appBottomPanel.setLayout(
                new GridLayout(
                        new Size.SizeValue(40, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(40, GuiConstants.ENUM_SIZE.ABSOLUTE),
                        new Size.SizeValue(0.06f, GuiConstants.ENUM_SIZE.RELATIVE),
                        GridLayout.GridDirection.HORIZONTAL,
                        4
                )
        );

        AtomicInteger displayedBottomApps = new AtomicInteger();
        AppManager.getApps().forEach((app) -> {

            if (!Objects.isNull(app.getGui())) {
                if (app.getGui().getClass().isAnnotationPresent(AppDetails.class)) {

                    AppDetails[] appType = app.getGui().getClass().getAnnotationsByType(AppDetails.class);

                    if(appType.length != 0) {
                        if (appType[0].type().equals(AppType.DOWNLOADABLE)) return;
                        if (appType[0].isAlwaysHidden()) return;
                    }
                }
            }


            GuiPanel appPanel = new GuiPanel();
            appPanel.setCssClass(app.getDefaultInAppBar() ? "app_bottom" : "app");
            appPanel.getStyle().setTexture(new GuiTextureSprite(app.getIcon()));
            appPanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                if (app.getGui() != null) {
                    Minecraft.getMinecraft().displayGuiScreen(app.getGui());
                }
                if (app.getRunnable() != null) {
                    app.getRunnable().run();
                }
            });

            if (app.getDefaultInAppBar()) appBottomPanel.add(appPanel);
            else appListPanel.add(appPanel);

            if (displayedBottomApps.get() >= 4) {
                SPhone.logger.warn("Too many apps in bottom bar, some will be hidden");
            }


            if (app.getDefaultInAppBar()) displayedBottomApps.getAndIncrement();
        });

        getBackground().add(appListPanel);
        getBackground().add(appBottomPanel);
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
            Thread t = new Thread(() -> {
                try {
                    GuiLabel label = new GuiLabel("Developper mode enabled.");
                    label.setCssCode("a","bottom: 5%;color:red;");
                    getBackground().add(label);
                    Thread.sleep(1000);
                    getBackground().remove(label);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
            DEVMODE_LOCAL = true;
            AppManager.reloadApps(this.getGuiScreen());

        }
    }
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/home.css"));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }

}
