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

public class GuiWaitCall extends GuiBase {

    private GuiLabel time;

    public GuiWaitCall(String s) {
        super(new GuiHome().getGuiScreen());

        time = new GuiLabel("Appel en cours");
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

    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }
}
