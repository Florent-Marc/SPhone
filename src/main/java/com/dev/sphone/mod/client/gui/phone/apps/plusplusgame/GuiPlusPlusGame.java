package com.dev.sphone.mod.client.gui.phone.apps.plusplusgame;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@AppDetails(type = AppType.DOWNLOADABLE)
public class GuiPlusPlusGame extends GuiBase {

    public GuiPlusPlusGame(GuiScreen parent) {
        super(parent);
    }

    int points = 0;

    GuiLabel counter;

    @Override
    public void GuiInit() {
        super.GuiInit();

        counter = new GuiLabel(I18n.format("sphone.plusplusgame.points") + " 0");
        counter.setCssId("counter");
//        GuiLabel appTitle = new GuiLabel("Plus Plus Game");
//        appTitle.setCssId("app_title");
//        this.getRoot().add(appTitle);

        GuiPanel gamePanel = new GuiPanel();
        gamePanel.setCssClass("game_panel");

        getRoot().add(gamePanel);

        Thread th = new Thread(() -> {
            try {
                Thread.sleep(100);
                drawRandomButton(gamePanel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        th.start();

        getRoot().add(counter);

        add(this.getRoot());


    }

    public void drawRandomButton(GuiPanel backframe) {
        GuiPanel button = new GuiPanel();
        int wmax = backframe.getWidth() - 50;
        int hmax = backframe.getHeight() - 50;


        System.out.println("Max size " + wmax + " " + hmax);
        int x = Math.max(50, (int) (Math.random() * wmax));
        int y = Math.max(50, (int) (Math.random() * hmax));

        button.setCssClass("button");
        button.getStyle().setOffsetX(x);
        button.getStyle().setOffsetY(y);

        System.out.println("Button at " + x + " " + y);

        button.addClickListener((mouseX, mouseY, mouseButton) -> {
            points++;
            backframe.remove(button);
            backframe.getStyle().setBackgroundColor(0xFF000000 + (int) (Math.random() * 0xFFFFFF));
            counter.setText(I18n.format("sphone.plusplusgame.points") + " " +  points);
            drawRandomButton(backframe);
        });

        backframe.add(button);


    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/plusplusgame.css"));
        return styles;
    }

}
