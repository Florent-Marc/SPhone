package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.call.PacketCallResponse;
import com.dev.sphone.mod.common.register.SoundRegister;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;

import java.util.ArrayList;
import java.util.List;

public class GuiWaitCall extends GuiBase {

    private final String name;
    private final String receiver;
    private GuiLabel time;
    private int tick = 0;

    public GuiWaitCall(GuiScreen parent, String name, String receiver) {
        super(parent);
        this.name = name;
        this.receiver = receiver;
        tick = 0;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        getBackground().removeAllChildren();
        time = new GuiLabel(I18n.format("sphone.phone.running"));
        time.setCssId("time");
        getBackground().add(time);
        mc.getSoundHandler().stop("sphone:ringtone", SoundCategory.MASTER);

        GuiPanel ButtonDecline = new GuiPanel();
        ButtonDecline.setCssClass("button_decline");
        ButtonDecline.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
            SPhone.network.sendToServer(new PacketCallResponse(receiver));
        });
        getBackground().add(ButtonDecline);

        GuiLabel number = new GuiLabel(name);
        number.setCssId("number");
        getBackground().add(number);
    }

    @Override
    public void guiClose() {
        //SPhone.network.sendToServer(new PacketQuitCall(name));
    }

    @Override
    public boolean tick() {
        super.tick();
        tick ++;
        String a = I18n.format("sphone.phone.running");
        String b = I18n.format("sphone.phone.running")+"..";
        String c = I18n.format("sphone.phone.running")+"..";
        String d = I18n.format("sphone.phone.running")+"...";

        if (tick % 10 == 0) {
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
        if (tick % 45*3 == 0) {
            mc.player.playSound(SoundRegister.CALL, 1, 1);
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
