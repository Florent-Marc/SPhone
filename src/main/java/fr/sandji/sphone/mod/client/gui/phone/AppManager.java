package fr.sandji.sphone.mod.client.gui.phone;

import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.apps.calculator.GuiCalculator;
import fr.sandji.sphone.mod.client.gui.phone.apps.call.GuiCall;
import fr.sandji.sphone.mod.client.gui.phone.apps.contacts.GuiContactsList;
import fr.sandji.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import fr.sandji.sphone.mod.client.gui.phone.apps.note.GuiNoteList;
import fr.sandji.sphone.mod.common.items.ItemPhone;
import fr.sandji.sphone.mod.common.packets.server.PacketRequestData;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import fr.sandji.sphone.mod.common.phone.Message;
import fr.sandji.sphone.mod.common.phone.Note;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class AppManager {
    private static final List<App> apps = new ArrayList<>();
    /**
     * @apiNote Add only the guis who there are to be displayed in home.
     * @author gabidut76
     */
    public static void init(GuiScreen root) {

        Supplier<GuiScreen> guiSupplier = () -> new GuiNoteList(root, Collections.singletonList(new Note("Hello", "Coucou", System.currentTimeMillis()))).getGuiScreen();
        // TODO: Get notes in phone
        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/notes.png"),
                "Notes",
                "1.0",
                false,
                true,
                () -> SPhone.network.sendToServer(new PacketRequestData("notes", String.valueOf(ItemPhone.getSimCard(root.mc.player.getHeldItemMainhand()))))
        ));
        
        // TODO: Remove that

        List<Conversation> conversations = new ArrayList<>();
        Conversation test = new Conversation();
        Message message = new Message("Rdv bar de la place",1688395401123L ,1,2);
        Message mess = new Message("okay ma poule",1688499414507L ,2,1);
        Message mess1 = new Message("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",1688500163006L ,1,2);
        Message mess2 = new Message("dans le bar a droite",1688500173006L ,2,1);
        Message mess3 = new Message("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",1688500173006L ,1, 2);
        Message mess4 = new Message("Salut ça c'est 160 charactères voilà c'est génial. Sinon je suis vraiment beau gosse et très très intelligent et aussi très charismatique",1688500173006L , 2, 1);
        Message mess5 = new Message("AAAAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAA",1688500173006L ,1, 1);
        Message mess6 = new Message("Salut ça c'est 160 charactères voilà c'est génial. Sinon je suis vraiment beau gosse et très très intelligent et aussi très charismatique Salut ça c'est 160 charactères voilà c'est génial. Sinon je suis vraiment vraiment vraiment",1688500173006L ,1, 1);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(mess);
        messages.add(mess1);
        messages.add(mess2);
        messages.add(mess3);
        messages.add(mess4);
        messages.add(mess5);
        messages.add(mess6);
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

        guiSupplier = () -> new GuiConvList(root, conversations).getGuiScreen();

        apps.add(new App(
                guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/message.png"),
                "Messages",
                "1.0",
                false,
                false,
                null
        ));

        guiSupplier = () -> new GuiContactsList(root, Collections.singletonList(new Contact("caca", "Jean Michel",1234))).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/contacts.png"),
                "Contacts",
                "1.0",
                false,
                true,
                null
        ));

        guiSupplier = () -> new GuiCall(root, "caca").getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/call.png"),
                "Téléphone",
                "1.0",
                false,
                true,
                null
        ));

        guiSupplier = () -> new GuiCalculator(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/calculator.png"),
                "Calculatrice",
                "1.0",
                false,
                false,
                null
        ));


    }

    public static void reloadApps(GuiScreen root) {
        apps.clear();
        init(root);
    }


    public static List<App> getApps() {
        return apps;
    }

    public static class App {
        Supplier<GuiScreen> gui;
        ResourceLocation icon;
        String name;
        String version; // don't care it's just for "realism"
        boolean  showInDebug = false; // only for dev
        boolean  defaultInAppBar = false;
        Runnable  runnable;

        /**
         * @param gui             Gui to display
         * @param icon            Icon to display
         * @param name            Name of the app
         * @param version         Version of the app (don't care it's just for "realism")
         * @param showInDebug     If true, the app will be displayed in debug mode
         * @param defaultInAppBar If true, the app will be displayed in the app bar
         */
        public App(Supplier<GuiScreen> gui, ResourceLocation icon, String name, String version, boolean showInDebug, boolean defaultInAppBar, @Nullable Runnable runnable) {
            this.gui = gui;
            this.icon = icon;
            this.name = name;
            this.version = version;
            this.showInDebug = showInDebug;
            this.defaultInAppBar = defaultInAppBar;
            this.runnable = runnable;
        }

        public GuiScreen getGui() {
            if(gui != null) {
                return gui.get();
            }
            return null;
        }

        public void setGui(GuiScreen gui) {
            //this.gui.gui;
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

        public boolean getShowInDebug() {
            return showInDebug;
        }

        public void setShowInDebug(boolean showInDebug) {
            this.showInDebug = showInDebug;
        }

        public boolean getDefaultInAppBar() {
            return defaultInAppBar;
        }

        public void setDefaultInAppBar(boolean defaultInAppBar) {
            this.defaultInAppBar = defaultInAppBar;
        }

        public Runnable getRunnable() {
            return runnable;
        }
    }
}
