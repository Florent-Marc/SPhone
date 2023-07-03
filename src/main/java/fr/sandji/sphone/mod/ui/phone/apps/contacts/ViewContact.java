/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.ui.phone.apps.contacts;

import fr.aym.acsguis.component.layout.GuiScaler;
import fr.aym.acsguis.component.panel.GuiFrame;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.aym.acsguis.event.listeners.mouse.IMouseMoveListener;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.ui.phone.Home;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ViewContact extends GuiFrame {

    public ViewContact() {
        super(new GuiScaler.AdjustFullScreen());
        style.setBackgroundColor(Color.TRANSLUCENT);
        setCssClass("home");

        GuiLabel Background = new GuiLabel("");
        Background.setCssId("background");
        add(Background);

        GuiLabel Case = new GuiLabel("");
        Case.setCssId("case");
        add(Case);

        GuiLabel TopClock = new GuiLabel("");
        TopClock.setCssId("top_clock");
        TopClock.addTickListener(() -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date();
            TopClock.setText(dateFormat.format(date));
        });
        add(TopClock);

        GuiLabel TopIcons = new GuiLabel("");
        TopIcons.setCssId("top_icons");
        add(TopIcons);

        GuiLabel HomeBar = new GuiLabel("");
        HomeBar.setCssId("home_bar");
        HomeBar.addClickListener((x,y,bu) -> {
            //Minecraft.getMinecraft().displayGuiScreen(new Contacts().getGuiScreen());
        });
        add(HomeBar);

        GuiLabel AppTitle = new GuiLabel("Contact");
        AppTitle.setCssId("app_title");
        add(AppTitle);

        GuiLabel ButtonEdit = new GuiLabel("✎");
        ButtonEdit.setCssId("button_add");
        add(ButtonEdit);

        GuiLabel ContactAvatar = new GuiLabel("");
        ContactAvatar.setCssId("view_contact_avatar");
        String cssCode = "background-image: url(\"https://mc-heads.net/avatar/" + "0hSandji" + "\");";
        ContactAvatar.setCssCode("view_contact_avatar", cssCode);
        add(ContactAvatar);

    }

    public List<ResourceLocation> getCssStyles() {
        return Collections.singletonList(new ResourceLocation("sphone:css/contacts.css"));
    }

    @Override
    public boolean needsCssReload() {
        return SPhone.DEV_MOD;
    }

    @Override
    public boolean doesPauseGame() {
        return false;
    }

}
