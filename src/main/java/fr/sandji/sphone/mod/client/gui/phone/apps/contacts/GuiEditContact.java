/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.contacts;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiEditContact extends GuiBase {

    private final List<Contact> contacts;
    private final Contact contact;

    public GuiEditContact(GuiScreen parent, List<Contact> contacts, Contact contact) {
        super(parent);

        this.contacts = contacts;
        this.contact = contact;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();
        GuiLabel AppTitle = new GuiLabel("Modifier un contact");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);


        GuiTextField nom = new GuiTextField();
        nom.setCssClass("nom");
        nom.setText(contact.getName());
        getBackground().add(nom);

        GuiTextField prenom = new GuiTextField();
        prenom.setCssClass("prenom");
        prenom.setText(contact.getLastname());
        getBackground().add(prenom);

        GuiTextField numero = new GuiTextField();
        numero.setCssClass("numero");
        numero.setText(String.valueOf(contact.getNumero()));
        getBackground().add(numero);

        //notes
        GuiTextField notes = new GuiTextField();
        notes.setCssClass("notes");
        if(contact.getNotes() == null) {
            notes.setHintText("➜ Notes");
        } else {
            notes.setText(contact.getNotes());
        }
        getBackground().add(notes);

        GuiPanel ButtonAdd = new GuiPanel();
        ButtonAdd.setCssClass("edit");
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
