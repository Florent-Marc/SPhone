/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.note;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiEditContact;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiNoteList extends GuiBase {

    public GuiNoteList(List<Note> n) {
        super(new GuiHome().getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Notes");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiLabel ButtonEdit = new GuiLabel("+");
        ButtonEdit.setCssId("button_add");
        getBackground().add(ButtonEdit);
        ButtonEdit.addClickListener((mouseX, mouseY, mouseButton) -> {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNewNote(n).getGuiScreen());
        });

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 80, 5, GridLayout.GridDirection.HORIZONTAL, 1));


        //trier les conversations par date de dernier message
       // n.sort((o1, o2) -> getDate(o2.getLastUpdate()).compareTo(getDate(o1.getLastUpdate())));

        for (Note c : n) {

            GuiPanel convpanel = new GuiPanel();
            convpanel.setCssClass("back");
            convpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiNote(n,c).getGuiScreen());
            });
            GuiLabel ContactName = new GuiLabel(c.getTitle());
            ContactName.setCssId("title");
            convpanel.add(ContactName);

            GuiLabel ContactLastMessage = new GuiLabel(c.getText());
            ContactLastMessage.setCssId("lastmessage");
            convpanel.add(ContactLastMessage);

            GuiLabel date = new GuiLabel(getDate(c.getDate()));
            date.setCssId("date");
            convpanel.add(date);

            contacts_list.add(convpanel);
        }

        getBackground().add(contacts_list);

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
        styles.add(new ResourceLocation("sphone:css/convlist.css"));
        return styles;
    }

}
