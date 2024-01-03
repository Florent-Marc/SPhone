package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketQuitCall;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;

import java.util.ArrayList;
import java.util.List;

public class GuiCall extends GuiBase {

    private final String s;
    private long Timestart;
    private GuiLabel time;

    public GuiCall(GuiScreen parent, String s) {
        super(new GuiHome().getGuiScreen());
        this.s = s;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        mc.player.sendMessage(new TextComponentString("A"));
        Timestart = System.currentTimeMillis();
        time = new GuiLabel("Appel terminé");
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
                Minecraft.getMinecraft().getSoundHandler().stop("sphone:nonattrib", SoundCategory.MASTER);
                Minecraft.getMinecraft().getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
                Minecraft.getMinecraft().getSoundHandler().stop("sphone:unjoinable", SoundCategory.MASTER);
                Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
                SPhone.network.sendToServer(new PacketQuitCall());
            }
        });
        getBackground().add(exit);
    }

    @Override
    public void guiClose() {
        super.guiClose();
        Minecraft.getMinecraft().getSoundHandler().stop("sphone:nonattrib", SoundCategory.MASTER);
        Minecraft.getMinecraft().getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
        Minecraft.getMinecraft().getSoundHandler().stop("sphone:unjoinable", SoundCategory.MASTER);
    }



    @Override
    public void tick() {
        super.tick();
        long time = System.currentTimeMillis() - Timestart;
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        this.time.setText(String.format("%02d:%02d", minutes, seconds));
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }
}
