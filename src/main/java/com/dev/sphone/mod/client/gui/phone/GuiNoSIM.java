package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.cssengine.positionning.Size;
import fr.aym.acsguis.utils.GuiConstants;
import fr.aym.acsguis.utils.GuiTextureSprite;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GuiNoSIM extends GuiBase {
    private String reason;

    public GuiNoSIM() {
        super();
    }
    public GuiNoSIM(String reason) {
        super();
        this.reason = reason;
    }

    @Override
    public void GuiInit(){
        super.GuiInit();

        setInfosHidden(true);


        GuiLabel appTitle = new GuiLabel(I18n.format("sphone.error.sim"));
        appTitle.setCssId("app_title");

        GuiPanel icon = new GuiPanel();
        icon.setCssId("app_icon");

        GuiLabel appDesc = new GuiLabel(I18n.format("sphone.error.desc"));
        appDesc.setCssId("app_desc");

        if(reason.equals("unregistred")) {
            appDesc.setText(I18n.format("sphone.error.unregistred"));
            getRoot().add(appDesc);
        } else {
            appDesc.setText(I18n.format("sphone.error.notinserted"));
        }
        getRoot().add(appDesc);
        getRoot().add(appTitle);
        getRoot().add(icon);
        add(getRoot());

    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();

        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/nosim.css"));
        return styles;
    }

}
