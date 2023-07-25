/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiNote extends GuiBase {


    public GuiNote(GuiScreen parent, List<Note> l, Note contact) {
        super(parent);
        GuiLabel AppTitle = new GuiLabel(contact.getTitle());
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonEdit = new GuiLabel("✎");
        ButtonEdit.setCssId("button_add");
        getBackground().add(ButtonEdit);
        ButtonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditNote(this.getGuiScreen(), l,contact).getGuiScreen());
        });


        GuiLabel notes = new GuiLabel("Notes : "+contact.getText());
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
