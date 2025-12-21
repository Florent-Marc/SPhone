package com.dev.sphone.mod.client.gui.phone.apps.quickpay;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple standalone transfer app that does not rely on the bank database.
 */
public class GuiQuickPay extends GuiBase {

    public GuiQuickPay(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(getRoot());

        GuiLabel title = new GuiLabel(I18n.format("sphone.quickpay.title"));
        title.setCssId("app_title");
        getRoot().add(title);

        GuiTextField receiver = new GuiTextField();
        receiver.setCssClass("input_receiver");
        receiver.setHintText(I18n.format("sphone.quickpay.recipient"));
        receiver.setMaxTextLength(20);
        getRoot().add(receiver);

        GuiTextField amount = new GuiTextField();
        amount.setCssClass("input_amount");
        amount.setHintText(I18n.format("sphone.quickpay.amount"));
        amount.setMaxTextLength(20);
        getRoot().add(amount);

        GuiLabel send = new GuiLabel(I18n.format("sphone.quickpay.send"));
        send.setCssId("send_button");
        send.addClickListener((mouseX, mouseY, mouseButton) ->
                Minecraft.getMinecraft().player.sendChatMessage("/pay " + receiver.getText() + " " + amount.getText())
        );
        getRoot().add(send);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/quickpay.css"));
        return styles;
    }
}