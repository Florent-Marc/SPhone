/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiViewContact;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuiConvList extends GuiBase {

    public GuiConvList(List<Conversation> conv) {
        super(new GuiHome().getGuiScreen());

        GuiLabel AppTitle = new GuiLabel("Messages");
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiScrollPane contacts_list = new GuiScrollPane();
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 80, 5, GridLayout.GridDirection.HORIZONTAL, 1));


        //trier les conversations par date de dernier message
        conv.sort((o1, o2) -> getDate(o2.getLastUpdate()).compareTo(getDate(o1.getLastUpdate())));

        for (Conversation c : conv) {

            GuiPanel convpanel = new GuiPanel();
            convpanel.setCssClass("contact_background");
            convpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
            });
            GuiLabel ContactName = new GuiLabel(c.getSender().getName());
            ContactName.setCssId("name");
            convpanel.add(ContactName);

            GuiLabel ContactLastMessage = new GuiLabel(c.getLastMessage().getMessage());
            ContactLastMessage.setCssId("lastmessage");
            convpanel.add(ContactLastMessage);

            GuiLabel date = new GuiLabel(getDate(c.getLastUpdate()));
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
