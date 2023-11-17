package com.dev.sphone.mod.client.gui.phone.apps.call;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.packets.server.call.PacketCallRequest;
import com.dev.sphone.mod.common.packets.server.call.gabiwork.PacketSendRequestCall;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiMakeCall extends GuiBase {

    String numberinput = "";

    public GuiMakeCall(GuiScreen parent) {
        super(parent);
    }


    /**
     *
     * @apiNote DON'T USE THIS CODE TO DUPLICATE THE CALL APP.
     *
     */
    @Override
    public void GuiInit() {
        super.GuiInit();
        add(getRoot());
        GuiPanel call = new GuiPanel();
        call.setCssClass("call");
        call.addClickListener((mouseX, mouseY, mouseButton) -> {
            if (mouseButton == 0) {
                SPhone.network.sendToServer(new PacketSendRequestCall(numberinput));
            }
        });

        GuiPanel callZone = new GuiPanel();
        callZone.setCssClass("callzone");

        GuiLabel nbInput = new GuiLabel(numberinput);
        nbInput.setCssClass("nbinput");
        callZone.add(nbInput);

        GuiPanel buttons = new GuiPanel();
        buttons.setCssClass("buttons");
        buttons.setLayout(new GridLayout(
                68,
                68,
                1,
                GridLayout.GridDirection.HORIZONTAL,
                3
        ));

        for (int i = 1; i <= 9; i++) {
            GuiPanel button = new GuiPanel();
            button.setCssClass("button");

            GuiLabel label = new GuiLabel(String.valueOf(i));
            label.setCssClass("label");
            button.add(label);

            int finalI = i;
            button.addClickListener((mouseX, mouseY, mouseButton) -> {
                numberinput += String.valueOf(finalI);
                nbInput.setText(numberinput);
            });

            buttons.add(button);
        }

            GuiPanel button = new GuiPanel();
            button.setCssClass("buttonspe");
            GuiLabel label = new GuiLabel("0");
            label.setCssClass("label");
            button.add(label);
            button.addClickListener((mouseX, mouseY, mouseButton) -> {
                numberinput += "0";
                nbInput.setText(numberinput);
            });

            buttons.add(button);

            GuiPanel starButton = new GuiPanel();
            starButton.setCssClass("buttonspe");
            GuiLabel starLabel = new GuiLabel("*");
            starLabel.setCssClass("label");
            starButton.add(starLabel);
            starButton.addClickListener((mouseX, mouseY, mouseButton) -> {
                numberinput += "*";
                nbInput.setText(numberinput);
            });
            buttons.add(starButton);

            GuiPanel diezButton = new GuiPanel();
            diezButton.setCssClass("buttonspe");
            GuiLabel diezLabel = new GuiLabel("#");
            diezLabel.setCssClass("label");
            diezButton.add(diezLabel);
            diezButton.addClickListener((mouseX, mouseY, mouseButton) -> {
                numberinput += "#";
                nbInput.setText(numberinput);
            });
            buttons.add(diezButton);



        callZone.add(buttons);
        getRoot().add(callZone);
        getRoot().add(call);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/call.css"));
        return styles;
    }
}
