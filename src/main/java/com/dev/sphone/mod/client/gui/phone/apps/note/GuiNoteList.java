package com.dev.sphone.mod.client.gui.phone.apps.note;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import com.dev.sphone.mod.common.phone.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiNoteList extends GuiBase {

    private final List<Note> notes;

    public GuiNoteList(GuiScreen parent, List<Note> notes) {
        super(parent);
        this.notes = notes;
    }

    @Override
    public void GuiInit(){
        super.GuiInit();

        add(getRoot());

        GuiLabel appTitle2 = new GuiLabel("Notes");
        appTitle2.setCssId("app_title");
        getRoot().add(appTitle2);

        GuiLabel buttonEdit2 = new GuiLabel("+");
        buttonEdit2.setCssId("button_add");
        getRoot().add(buttonEdit2);
        buttonEdit2.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewNote(this.getGuiScreen()).getGuiScreen());
        });

        GuiScrollPane contacts_list2 = new GuiScrollPane();
        contacts_list2.setCssClass("contacts_list");
        contacts_list2.setLayout(new GridLayout(-1, 80, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        //trier les conversations par date de dernier message
        // n.sort((o1, o2) -> getDate(o2.getLastUpdate()).compareTo(getDate(o1.getLastUpdate())));

        for (Note note : notes) {

            GuiPanel convpanel = new GuiPanel();
            convpanel.setCssClass("back");
            convpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiNote(this.getGuiScreen(), note).getGuiScreen());
            });
            GuiLabel ContactName = new GuiLabel(note.getTitle());
            ContactName.setCssId("title");
            convpanel.add(ContactName);

            GuiLabel ContactLastMessage = new GuiLabel(note.getText());
            ContactLastMessage.setCssId("lastmessage");
            convpanel.add(ContactLastMessage);

            GuiLabel date = new GuiLabel(getDate(note.getDate()));
            date.setCssId("date");
            convpanel.add(date);

            contacts_list2.add(convpanel);
        }
        getRoot().add(contacts_list2);
    }

    //get date with long
    public String getDate(long date) {
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/notelist.css"));
        return styles;
    }

}
