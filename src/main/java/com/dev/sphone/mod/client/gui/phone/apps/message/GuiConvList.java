package com.dev.sphone.mod.client.gui.phone.apps.message;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.phone.Conversation;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.utils.UtilsServer;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GuiConvList extends GuiBase {

    private final List<Conversation> conv;

    public GuiConvList(List<Conversation> conv) {
        super(new GuiHome().getGuiScreen());
        // Garante que conv nunca será null
        this.conv = (conv != null) ? conv : new ArrayList<>();
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        GuiLabel AppTitle = new GuiLabel(I18n.format("sphone.message.title"));
        AppTitle.setCssId("app_title");
        getBackground().add(AppTitle);

        GuiScrollPane conversations_list = new GuiScrollPane();
        conversations_list.setCssClass("contacts_list");
        conversations_list.setLayout(new GridLayout(-1, 80, 5, GridLayout.GridDirection.HORIZONTAL, 1));

        // Ordena as conversas por data do último message, só se houver conversas
        if (!conv.isEmpty()) {
            Collections.sort(conv, new Comparator<Conversation>() {
                @Override
                public int compare(Conversation c1, Conversation c2) {
                    Message m1 = c1.getLastMessage();
                    Message m2 = c2.getLastMessage();
                    if (m1 == null && m2 == null) return 0;
                    if (m1 == null) return 1;
                    if (m2 == null) return -1;
                    return Long.compare(m2.getDate(), m1.getDate());
                }
            });
        }

        List<Conversation> def = new ArrayList<>(conv);

        for (Conversation c : def) {
            List<Message> messages = c.getMessages();
            if (messages == null || messages.isEmpty()) continue; // Evita crashes

            Message lastMessage = messages.get(messages.size() - 1);

            GuiPanel convpanel = new GuiPanel();
            convpanel.setCssClass("contact_background");
            convpanel.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiConv(this.getGuiScreen(), c).getGuiScreen());
            });

            GuiLabel ContactName = new GuiLabel(c.getSender().getName());
            ContactName.setCssId("name");
            convpanel.add(ContactName);

            GuiLabel ContactLastMessage = new GuiLabel(lastMessage.getMessage());
            ContactLastMessage.setCssId("lastmessage");
            convpanel.add(ContactLastMessage);

            GuiLabel date = new GuiLabel(UtilsServer.getDateOf(lastMessage.getDate()));
            date.setCssId("date");
            convpanel.add(date);

            conversations_list.add(convpanel);
        }

        getBackground().add(conversations_list);
    }

    @Override
    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/convlist.css"));
        return styles;
    }
}
