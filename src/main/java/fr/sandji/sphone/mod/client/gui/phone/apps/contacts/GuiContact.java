/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.client.gui.phone.GuiInit;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiContact extends GuiInit {


    public GuiContact(List<Contact> l,Contact contact) {
        super(new GuiContactsList(l).getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Contact");
        AppTitle.setCssId("app_title");
        add(AppTitle);

        GuiLabel ButtonEdit = new GuiLabel("✎");
        ButtonEdit.setCssId("button_add");
        add(ButtonEdit);

        GuiLabel ContactAvatar = new GuiLabel("");
        ContactAvatar.setCssId("view_contact_avatar");
        String cssCode = "background-image: url(\"https://mc-heads.net/avatar/" + contact.getPlayer_associated() + "\");";
        ContactAvatar.setCssCode("view_contact_avatar", cssCode);
        add(ContactAvatar);

    }


    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contacts.css"));
        return styles;
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
