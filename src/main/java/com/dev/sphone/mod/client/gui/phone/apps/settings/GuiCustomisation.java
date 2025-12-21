package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.tempdata.PhoneSettings;
import com.dev.sphone.mod.common.packets.server.PacketSetBackground;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

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

        GuiLabel appTitle = new GuiLabel(I18n.format("sphone.settings.title"));
        appTitle.setCssId("app_title");

        GuiScrollPane backgrounds = new GuiScrollPane();
        backgrounds.setCssId("scroll");
        backgrounds.setLayout(new GridLayout(
                new Size.SizeValue(70, GuiConstants.ENUM_SIZE.ABSOLUTE),
                new Size.SizeValue(180, GuiConstants.ENUM_SIZE.ABSOLUTE),
                new Size.SizeValue(20, GuiConstants.ENUM_SIZE.ABSOLUTE),
                GridLayout.GridDirection.VERTICAL, 2
        ));

        setEnableDebugPanel(true);

        String[] backgroundsConfig = new String[0];

        try {
            backgroundsConfig = UtilsClient.getBackgrounds();
        } catch (Exception e) {
            Minecraft.getMinecraft().player.sendChatMessage(I18n.format("sphone.settings.errorbg"));
        }

        int index = 0;
        for (String s : backgroundsConfig) {
            String id = s.split(":")[0];
            String name = s.split(":")[1];

            GuiPanel backgroundpanel = new GuiPanel();
            backgroundpanel.setCssClass("background");
            // CORRIGIDO: use o Customizer para textura!
            backgroundpanel.getStyle().getCustomizer().setTexture(
                new GuiTextureSprite(new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/" + id + ".png"))
            );

            GuiLabel oscuridadLabel = new GuiLabel(name);
            oscuridadLabel.setCssClass("background_label" + (settings.getBackground().equals(id) ? "_selected" : ""));
            // CORRIGIDO: use o Customizer para cor de fundo!
            if (index % 2 == 0) {
                oscuridadLabel.getStyle().getCustomizer().setBackgroundColor(0xFF00FFFF);
            } else {
                oscuridadLabel.getStyle().getCustomizer().setBackgroundColor(0x00FFFFFF);
            }
            backgroundpanel.add(oscuridadLabel); // Adiciona o label DENTRO do painel

            backgroundpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                settings.setBackground(id);
                SPhone.network.sendToServer(new PacketSetBackground(id));
            });
            backgrounds.add(backgroundpanel);
            index++;
        }

        app_container.add(backgrounds);
        app_container.add(appTitle);
        getRoot().add(app_container);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/settings.css"));
        return styles;
    }
}
