package com.dev.sphone.mod.client.gui.phone.apps.note;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.common.phone.Note;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiNote extends GuiBase {

    private final Note note;

    public GuiNote(GuiScreen parent, Note note) {
        super(parent);
        this.note = note;
    }

    @Override
    public void GuiInit(){
        super.GuiInit();

        add(getRoot());

        GuiLabel AppTitle = new GuiLabel(note.getTitle());
        AppTitle.setCssId("app_title");
        getRoot().add(AppTitle);

        GuiLabel buttonEdit = new GuiLabel("✎");
        buttonEdit.setCssId("button_add");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditNote(this.getGuiScreen(), note).getGuiScreen());
        });
        getRoot().add(buttonEdit);


        GuiLabel note = new GuiLabel("Note : "+ this.note.getText());
        note.setCssId("note");
        getRoot().add(note);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/note.css"));
        return styles;
    }

}
