package fr.sandji.sphone.mod.client.gui.phone;

import fr.aym.acsguis.component.panel.GuiFrame;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.apps.calculator.GuiCalculator;
import fr.sandji.sphone.mod.client.gui.phone.apps.call.GuiCall;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import fr.sandji.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import fr.sandji.sphone.mod.client.gui.phone.apps.note.GuiNote;
import fr.sandji.sphone.mod.client.gui.phone.apps.note.GuiNoteList;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import fr.sandji.sphone.mod.common.phone.Message;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiMessageDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppManager {
    private static List<App> Apps = new ArrayList<>();

    /**
     * @apiNote Add only the guis who there are to be displayed in home.
     * @author gabidut76
     */
    public static void init() {

        // TODO: Get notes in phone
        Apps.add(new App(
                new GuiNoteList(Collections.singletonList(new Note("Hello", "Coucou", System.currentTimeMillis()))).getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/notes.png"),
                "Notes",
                "1.0",
                false,
                true
        ));
        
        // TODO: Remove that

        List<Conversation> conversations = new ArrayList<>();
        Conversation test = new Conversation();
        Message message = new Message("Rdv bar de la place",1688395401123L ,1,2);
        Message mess = new Message("okay ma poule",1688499414507L ,1,2);
        Message mess1 = new Message("t'es ou ?",1688500163006L ,1,2);
        Message mess2 = new Message("dans le bar a droite",1688500173006L ,1,2);
        Message mess3 = new Message("j'arrive",1688500173006L ,1, 2);
        Message mess4 = new Message("ok",1688500173006L , 1, 2);
        Message mess5 = new Message("je te vois pas",1688500173006L ,1, 1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(mess);
        messages.add(mess1);
        messages.add(mess2);
        messages.add(mess3);
        messages.add(mess4);
        messages.add(mess5);
        test.setMessages(messages);
        test.setLastUpdate(1688395401123L);
        test.setLastMessage(message);
        test.setSender(new Contact("michel",1215));
        Conversation test2 = new Conversation();
        Message message1 = new Message("vas mourir conard",1688482617877L ,1,2);
        List<Message> messages1 = new ArrayList<>();
        messages1.add(message1);
        test2.setMessages(messages1);
        test2.setLastUpdate(1688482617877L);
        test2.setLastMessage(message1);
        test2.setSender(new Contact("jup",1215));
        conversations.add(test);
        conversations.add(test2);

        Apps.add(new App(
                new GuiConvList(conversations).getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/message.png"),
                "Messages",
                "1.0",
                false,
                false
        ));

        Apps.add(new App(
                new GuiContactsList(
                        Collections.singletonList(new Contact("caca", "Jean Michel",1234))
                ).getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/contacts.png"),
                "Contacts",
                "1.0",
                false,
                true
        ));
        Apps.add(new App(
                new GuiCall("caca").getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/call.png"),
                "Téléphone",
                "1.0",
                false,
                true
        ));
        Apps.add(new App(
                new GuiCalculator().getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/calculator.png"),
                "Calculatrice",
                "1.0",
                false,
                false
        ));


    }

    public static void reloadApps() {
        Apps.clear();
        init();
    }


    public static List<App> getApps() {
        return Apps;
    }

    public static class App {
        GuiScreen gui;
        ResourceLocation icon;
        String name;
        String version; // don't care it's just for "realism"
        Boolean showInDebug = false; // only for dev
        Boolean defaultInAppBar = false;

        /**
         * @param gui             Gui to display
         * @param icon            Icon to display
         * @param name            Name of the app
         * @param version         Version of the app (don't care it's just for "realism")
         * @param showInDebug     If true, the app will be displayed in debug mode
         * @param defaultInAppBar If true, the app will be displayed in the app bar
         */
        public App(GuiScreen gui, ResourceLocation icon, String name, String version, Boolean showInDebug, Boolean defaultInAppBar) {
            this.gui = gui;
            this.icon = icon;
            this.name = name;
            this.version = version;
            this.showInDebug = showInDebug;
            this.defaultInAppBar = defaultInAppBar;
        }


        public GuiScreen getGui() {
            return gui;
        }

        public void setGui(GuiScreen gui) {
            this.gui = gui;
        }

        public ResourceLocation getIcon() {
            return icon;
        }

        public void setIcon(ResourceLocation icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Boolean getShowInDebug() {
            return showInDebug;
        }

        public void setShowInDebug(Boolean showInDebug) {
            this.showInDebug = showInDebug;
        }

        public Boolean getDefaultInAppBar() {
            return defaultInAppBar;
        }

        public void setDefaultInAppBar(Boolean defaultInAppBar) {
            this.defaultInAppBar = defaultInAppBar;
        }
    }
}
