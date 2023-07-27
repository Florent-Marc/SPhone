/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiNote extends GuiBase {

    private final Note contact;

    public GuiNote(GuiScreen parent, Note contact) {
        super(parent);
        this.contact = contact;
    }

    @Override
    public void GuiInit(){
        super.GuiInit();

        GuiPanel root = new GuiPanel();
        root.setCssId("root");
        add(root);

        GuiLabel AppTitle = new GuiLabel(contact.getTitle());
        AppTitle.setCssId("app_title");
        root.add(AppTitle);

        GuiLabel buttonEdit = new GuiLabel("✎");
        buttonEdit.setCssId("button_add");
        buttonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiEditNote(this.getGuiScreen(), contact).getGuiScreen());
        });
        root.add(buttonEdit);


        GuiLabel note = new GuiLabel("Note : "+contact.getText());
        note.setCssId("note");
        root.add(note);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/note.css"));
        return styles;
    }

}
