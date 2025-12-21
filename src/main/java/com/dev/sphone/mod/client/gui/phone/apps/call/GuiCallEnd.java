package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class GuiCallEnd extends GuiBase {

    private final String s;
    private GuiLabel time;
    private int tick = 0;

    public GuiCallEnd(GuiScreen parent, String s) {
        super(parent);
        this.s = s;
        tick = 0;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        time = new GuiLabel(I18n.format("sphone.phone.ended"));
        time.setCssId("time");
        time.setCssCode("color: red;");
        getBackground().add(time);

        GuiLabel number = new GuiLabel(s);
        number.setCssId("number");
        getBackground().add(number);
    }

    @Override
    public boolean tick() {
        super.tick();
        tick++;
        //wait 2 seconds
        if (tick % 80 == 0) {
            mc.displayGuiScreen(new GuiHome().getGuiScreen());
        }
        return true; // ou false, dependendo do que o método deve sinalizar
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }
}
