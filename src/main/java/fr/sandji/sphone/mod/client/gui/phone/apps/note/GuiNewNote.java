/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

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

public class GuiNewNote extends GuiBase {

    public GuiNewNote(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit(){
        super.GuiInit();

        add(getRoot());

        GuiLabel AppTitle = new GuiLabel("Ajouter Une Note");
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiTextField titre = new GuiTextField();
        titre.setCssClass("titre");
        titre.setHintText("➜ Titre");
        titre.setMaxTextLength(20);
        getRoot().add(titre);

        GuiTextField note = new GuiTextField(){
            @Override
            public boolean allowLineBreak() {
                return true;
            }
        };

        note.setCssClass("note");
        note.setHintText("➜ Note");
        note.setMaxTextLength(1000);
        getRoot().add(note);

        GuiLabel buttonEdit = new GuiLabel("+");
        buttonEdit.setCssId("button_add");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditNote(new Note(-1, titre.getText(), note.getText(), System.currentTimeMillis()), "add"));
        });
        getRoot().add(buttonEdit);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newnote.css"));
        return styles;
    }

}
