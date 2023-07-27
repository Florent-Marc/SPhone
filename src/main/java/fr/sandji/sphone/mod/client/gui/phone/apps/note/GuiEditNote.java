/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.packets.server.PacketEditNote;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiEditNote extends GuiBase {

    private final Note note;

    public GuiEditNote(GuiScreen parent, Note note) {
        super(parent);
        this.note = note;
    }
    
    @Override
    public void GuiInit(){
        super.GuiInit();

        GuiPanel root = new GuiPanel();
        root.setCssId("root");
        add(root);

        GuiLabel AppTitle = new GuiLabel("Modifier une note");
        AppTitle.setCssId("app_title");
        root.add(AppTitle);

        GuiTextField titre = new GuiTextField();
        titre.setCssClass("titre");
        titre.setText(note.getTitle());
        titre.setMaxTextLength(20);
        root.add(titre);

        GuiTextField noteField = new GuiTextField();
        noteField.setCssClass("note");
        noteField.setText(this.note.getText());
        noteField.setMaxTextLength(1000);
        root.add(noteField);

        GuiLabel buttonEdit = new GuiLabel("+");
        buttonEdit.setCssId("button_add");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditNote(new Note(note.getId(), titre.getText(), noteField.getText(), System.currentTimeMillis()), "edit"));
        });
        root.add(buttonEdit);

        GuiLabel buttonDel = new GuiLabel("-");
        buttonDel.setCssId("button_del");
        buttonDel.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditNote(new Note(note.getId(), titre.getText(), noteField.getText(), System.currentTimeMillis()), "delete"));
        });
        root.add(buttonDel);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newnote.css"));
        return styles;
    }

}
