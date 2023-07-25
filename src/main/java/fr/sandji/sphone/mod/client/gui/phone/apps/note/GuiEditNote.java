/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiEditNote extends GuiBase {

    private final List<Note> l;
    private final Note note;

    public GuiEditNote(GuiScreen parent, List<Note> l, Note note) {
        super(parent);
        this.l = l;
        this.note = note;
    }
    
    @Override
    public void GuiInit(){
        super.GuiInit();
        GuiLabel AppTitle = new GuiLabel("Modifier une note");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiTextField nom = new GuiTextField();
        nom.setCssClass("nom");
        nom.setText(note.getTitle());
        getBackground().add(nom);

        GuiTextField text = new GuiTextField();
        text.setCssClass("prenom");
        text.setText(note.getText());
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
