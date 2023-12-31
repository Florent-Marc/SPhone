package com.dev.sphone.mod.client.gui.phone.apps.note;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.packets.server.PacketEditNote;
import com.dev.sphone.mod.common.phone.Note;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
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

        add(getRoot());

        GuiLabel AppTitle = new GuiLabel(I18n.format("sphone.notes.edit"));
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiTextField titre = new GuiTextField();
        titre.setCssClass("titre");
        titre.setText(note.getTitle());
        titre.setMaxTextLength(20);
        getRoot().add(titre);

        GuiTextField noteField = new GuiTextField();
        noteField.setCssClass("note");
        noteField.setText(this.note.getText());
        noteField.setMaxTextLength(1000);
        getRoot().add(noteField);

        GuiLabel buttonEdit = new GuiLabel("✎");
        buttonEdit.setCssId("button_add");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditNote(new Note(note.getId(), titre.getText(), noteField.getText(), System.currentTimeMillis()), "edit"));
        });
        getRoot().add(buttonEdit);

        GuiLabel buttonDel = new GuiLabel("-");
        buttonDel.setCssId("button_del");
        buttonDel.setHoveringText(Collections.singletonList(I18n.format("sphone.notes.remove")));
        buttonDel.addClickListener((mouseX, mouseY, mouseButton) -> {
            SPhone.network.sendToServer(new PacketEditNote(new Note(note.getId(), titre.getText(), noteField.getText(), System.currentTimeMillis()), "delete"));
        });
        getRoot().add(buttonDel);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/newnote.css"));
        return styles;
    }

}
