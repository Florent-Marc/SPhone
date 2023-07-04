/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiNewContact extends GuiBase {

    public GuiNewContact(List<Contact> contacts) {
        super(new GuiContactsList(contacts).getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Ajouter un contact");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);


        GuiTextField nom = new GuiTextField();
        nom.setCssClass("nom");
        nom.setHintText("➜ nom");
        getBackground().add(nom);

        GuiTextField prenom = new GuiTextField();
        prenom.setCssClass("prenom");
        prenom.setHintText("➜ prenom");
        getBackground().add(prenom);

        GuiTextField numero = new GuiTextField();
        numero.setCssClass("numero");
        numero.setHintText("➜ 105665165");
        getBackground().add(numero);

        //notes
        GuiTextField notes = new GuiTextField();
        notes.setCssClass("notes");
        notes.setHintText("➜ Notes");
        getBackground().add(notes);

        GuiPanel ButtonAdd = new GuiPanel();
        ButtonAdd.setCssClass("button_add");
        ButtonAdd.addClickListener((mouseX, mouseY, mouseButton) -> {

        });
        getBackground().add(ButtonAdd);


    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newcontact.css"));
        return styles;
    }

}
