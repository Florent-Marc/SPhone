package fr.sandji.sphone.mod.client.gui.phone.apps.call;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.packets.server.call.PacketCallRequest;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiCallRequest extends GuiHome {

    private final String number;

    public GuiCallRequest(String number) {
        super();
        this.number = number;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        GuiLabel label = new GuiLabel("Appel entrant");
        label.setCssId("app_title");
        getBackground().add(label);

        GuiLabel numberLabel = new GuiLabel(number);
        numberLabel.setCssId("number");
        getBackground().add(numberLabel);

        GuiPanel ButtonAccept = new GuiPanel();
        ButtonAccept.setCssClass("button_accept");
        ButtonAccept.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketCallRequest(true));
            mc.displayGuiScreen(new GuiCall(this.getGuiScreen(), number).getGuiScreen());
        });
        getBackground().add(ButtonAccept);

        GuiPanel ButtonDecline = new GuiPanel();
        ButtonDecline.setCssClass("button_decline");
        ButtonDecline.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketCallRequest(false));
            mc.displayGuiScreen(new GuiCallEnd(this.getGuiScreen(), number).getGuiScreen());
        });
        getBackground().add(ButtonDecline);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(new ResourceLocation("sphone:css/base.css"));
        styles.add(new ResourceLocation("sphone:css/callrequest.css"));
        return styles;
    }
}
