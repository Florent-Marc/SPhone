/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiNewNote extends GuiBase {

    public GuiNewNote(List<Note> note) {
        super(new GuiNoteList(note).getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Ajouter une note");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);


        GuiTextField nom = new GuiTextField();
        nom.setCssClass("nom");
        nom.setHintText("➜ Titre");
        getBackground().add(nom);

        GuiTextField text = new GuiTextField();
        text.setCssClass("prenom");
        text.setHintText("➜ texte");
        text.setMaxTextLength(1000);
        getBackground().add(text);

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
