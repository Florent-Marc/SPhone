package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketQuitCall;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class GuiCall extends GuiBase {

    private final String s;
    private long Timestart;
    private GuiLabel time;

    public GuiCall(GuiScreen parent, String s) {
        super(new GuiHome().getGuiScreen());
        this.s = s;
        mc.getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        Timestart = System.currentTimeMillis();
        time = new GuiLabel(I18n.format("sphone.phone.ended"));
        time.setCssId("time");
        time.setCssCode("color: red;");
        getBackground().add(time);

        GuiLabel number = new GuiLabel(s);
        number.setCssId("number");
        getBackground().add(number);

        GuiPanel exit = new GuiPanel();
        exit.setCssClass("exit");
        exit.addClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                mc.getSoundHandler().stop("sphone:nonattrib", SoundCategory.MASTER);
                mc.getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
                mc.getSoundHandler().stop("sphone:unjoinable", SoundCategory.MASTER);
                mc.displayGuiScreen(new GuiHome().getGuiScreen());
                SPhone.network.sendToServer(new PacketQuitCall());
            }
        });
        getBackground().add(exit);
    }

    @Override
    public void guiClose() {
        super.guiClose();
        mc.getSoundHandler().stop("sphone:nonattrib", SoundCategory.MASTER);
        mc.getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
        mc.getSoundHandler().stop("sphone:unjoinable", SoundCategory.MASTER);
    }

    @Override
    public boolean tick() {
        super.tick();
        long time = System.currentTimeMillis() - Timestart;
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        this.time.setText(String.format("%02d:%02d", minutes, seconds));
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
