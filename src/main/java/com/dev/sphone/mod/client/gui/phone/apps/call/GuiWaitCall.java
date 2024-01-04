package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.register.SoundRegister;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class GuiWaitCall extends GuiBase {

    private final String name;
    private GuiLabel time;

    public GuiWaitCall(GuiScreen parent, String name) {
        super(parent);
        this.name = name;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        getBackground().removeAllChilds();
        time = new GuiLabel("Appel en cours");
        time.setCssId("time");
        getBackground().add(time);
        mc.getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);
        mc.getSoundHandler().stop("sphone:call", SoundCategory.MASTER);
        GuiPanel close = new GuiPanel();
        close.setCssClass("close");
        getBackground().add(close);
        close.addClickListener((p, m, b) -> {
            //SPhone.network.sendToServer(new PacketQuitCall(name));
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
        });


        GuiLabel number = new GuiLabel(name);
        number.setCssId("number");
        getBackground().add(number);
    }

    @Override
    public void guiClose() {
        super.guiClose();
        //SPhone.network.sendToServer(new PacketQuitCall(name));
    }

    @Override
    public void tick() {
        super.tick();

        String a = "Appel en cours";
        String b = "Appel en cours.";
        String c = "Appel en cours..";
        String d = "Appel en cours...";

        //tous les 10 ticks
        if (Minecraft.getMinecraft().world.getTotalWorldTime() % 10 == 0) {
            if (time.getText().equals(a)) {
                time.setText(b);
            } else if (time.getText().equals(b)) {
                time.setText(c);
            } else if (time.getText().equals(c)) {
                time.setText(d);
            } else if (time.getText().equals(d)) {
                time.setText(a);
            }
        }
        if (Minecraft.getMinecraft().world.getTotalWorldTime() % 50*3 == 0) {
            mc.player.playSound(SoundRegister.CALL, 1, 1);
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
