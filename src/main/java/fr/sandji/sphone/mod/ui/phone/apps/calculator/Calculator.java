
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.ui.phone.apps.calculator;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiFloatField;
import fr.aym.acsguis.component.textarea.GuiIntegerField;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Calculator extends GuiFrame {

    public Calculator() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        GuiLabel Background = new GuiLabel("");
        Background.setCssId("background");
        add(Background);

        GuiLabel Case = new GuiLabel("");
        Case.setCssId("case");
        add(Case);

        GuiLabel TopClock = new GuiLabel("");
        TopClock.setCssId("top_clock");
        TopClock.addTickListener(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            TopClock.setText(dateFormat.format(date));
        });
        add(TopClock);

        GuiLabel TopIcons = new GuiLabel("");
        TopIcons.setCssId("top_icons");
        add(TopIcons);

        GuiLabel HomeBar = new GuiLabel("");
        HomeBar.setCssId("home_bar");
        add(HomeBar);

        GuiFloatField Field = new GuiFloatField(0, 999999999);
        Field.setCssId("field");
        add(Field);

        GuiLabel Button_AC = new GuiLabel("AC");
        Button_AC.setCssId("button_ac");
        add(Button_AC);


    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/calculator.css"));
    }

    @Override
    public boolean needsCssReload() {
        return SPhone.DEV_MOD;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

}
