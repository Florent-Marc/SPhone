package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCallEnd extends GuiBase {

    private final String s;
    private GuiLabel time;

    public GuiCallEnd(GuiScreen parent, String s) {
        super(parent);
        this.s = s;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        time = new GuiLabel("Appel terminé");
        time.setCssId("time");
        time.setCssCode("color: red;");
        getBackground().add(time);

        GuiLabel number = new GuiLabel(s);
        number.setCssId("number");
        getBackground().add(number);
    }

    @Override
    public void tick() {
        super.tick();
        //wait 2 seconds
        if (Minecraft.getMinecraft().world.getTotalWorldTime() % 80 == 0) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
        }
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }
}
