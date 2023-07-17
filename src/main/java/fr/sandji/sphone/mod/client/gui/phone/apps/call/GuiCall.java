package fr.sandji.sphone.mod.client.gui.phone.apps.call;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.packets.server.call.PacketQuitCall;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCall extends GuiBase {

    private long Timestart;
    private GuiLabel time;

    public GuiCall(String s) {
        super(new GuiHome().getGuiScreen());

        Timestart = System.currentTimeMillis();

        time = new GuiLabel("00:00");
        time.setCssId("time");
        getBackground().add(time);

        GuiPanel close = new GuiPanel();
        close.setCssClass("close");
        getBackground().add(close);
        close.addClickListener((p, m, b) -> {
            SPhone.network.sendToServer(new PacketQuitCall());
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
        });


        GuiLabel number = new GuiLabel(s);
        number.setCssId("number");
        getBackground().add(number);

    }

    @Override
    public void guiClose() {
        super.guiClose();
        SPhone.network.sendToServer(new PacketQuitCall());
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
