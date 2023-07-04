/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client.gui.phone.apps.message;

import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import fr.aym.acsguis.component.textarea.GuiTextField;
import fr.sandji.sphone.mod.client.gui.phone.GuiBase;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import fr.sandji.sphone.mod.common.phone.Message;
import net.minecraft.util.ResourceLocation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GuiConv extends GuiBase {

    private static boolean load = false;

    public GuiConv(Conversation conv, Contact nous) {
        super(new GuiHome().getGuiScreen());

        GuiLabel AppTitle = new GuiLabel(conv.getSender().getName());
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiScrollPane contacts_list = new GuiScrollPane() {
            public double max=0;
            @Override
            public void updateSlidersVisibility() {
                super.updateSlidersVisibility();
                if (ySlider == null) return;
                if(max != ySlider.getMax()) {
                    max = ySlider.getMax();
                    ySlider.setValue(ySlider.getMax());
                }

            }
        };
        contacts_list.setCssClass("contacts_list");
        contacts_list.setLayout(new GridLayout(-1, 70, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        //trier les conversations par date de dernier message
        //conv.sort((o1, o2) -> getDate(o2.getLastUpdate()).compareTo(getDate(o1.getLastUpdate())));

        for (Message c : conv.getMessages()) {

            GuiPanel convpanel = new GuiPanel();

            GuiLabel ContactLastMessage = new GuiLabel(c.getMessage());
            convpanel.add(ContactLastMessage);
            //si le joueur passe sa souris sur le message, on affiche la date


            if (c.getSender().equals(nous)) {
                convpanel.setCssClass("contact_background_me");
                ContactLastMessage.setCssId("me_message");
            } else {
                convpanel.setCssClass("contact_background");
                ContactLastMessage.setCssId("contact_message");
            }

            GuiLabel date = new GuiLabel(getDate(c.getDate()));
            date.setCssId("date");
            //check si c'est aujourd'hui
            date.setHoveringText(Collections.singletonList(getHour(c.getDate()).toString()));
            convpanel.add(date);

            contacts_list.add(convpanel);

        }

        getBackground().add(contacts_list);

        GuiTextField message = new GuiTextField();
        message.setCssClass("message");
        message.setHintText("➜ votre message");
        getBackground().add(message);

        GuiPanel send = new GuiPanel();
        send.setCssClass("send");
        send.addClickListener((mouseX, mouseY, mouseButton) -> {
        });
        getBackground().add(send);
        contacts_list.getySlider().setValue(contacts_list.getySlider().getMax());
    }


    //get date et si c'est aujourd'hui on affiche l'heure
    public String getDate(long date) {
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(new Date());
        if (sdf.format(d).equals(today)) {
            sdf = new SimpleDateFormat("HH:mm");
        }
        return sdf.format(d);
    }

    //get heure
    public String getHour(long date) {
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(d);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/conv.css"));
        return styles;
    }

}
