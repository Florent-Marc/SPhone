/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiViewContact extends GuiBase {


    public GuiViewContact(List<Contact> l, Contact contact) {
        super(new GuiContactsList(l).getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Contact");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonEdit = new GuiLabel("✎");
        ButtonEdit.setCssId("button_add");
        getBackground().add(ButtonEdit);
        ButtonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditContact(l, contact).getGuiScreen());
        });

        GuiPanel message = new GuiPanel();
        message.setCssClass("message");
        getBackground().add(message);

        GuiPanel call = new GuiPanel();
        call.setCssClass("call");
        getBackground().add(call);

        GuiLabel ContactAvatar = new GuiLabel("");
        ContactAvatar.setCssId("view_contact_avatar");
        String cssCode = "background-image: url(\"https://mc-heads.net/avatar/" + contact.getPlayer_associated() + "\");";
        ContactAvatar.setCssCode("view_contact_avatar", cssCode);
        getBackground().add(ContactAvatar);

        GuiLabel name = new GuiLabel("Name : "+contact.getName());
        name.setCssId("name");
        getBackground().add(name);

        GuiLabel lastname = new GuiLabel("Lastname : "+contact.getLastname());
        lastname.setCssId("lastname");
        getBackground().add(lastname);

        GuiLabel phone = new GuiLabel("Phone : "+contact.getNumero());
        phone.setCssId("phone");
        getBackground().add(phone);

        GuiLabel notes = new GuiLabel("Notes : "+contact.getNotes());
        notes.setCssId("notes");
        getBackground().add(notes);

    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/contacts.css"));
        return styles;
    }

}
