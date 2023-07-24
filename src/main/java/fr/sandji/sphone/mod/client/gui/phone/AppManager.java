package fr.sandji.sphone.mod.client.gui.phone;

import fr.aym.acsguis.component.panel.GuiFrame;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.gui.phone.apps.message.GuiConvList;
import fr.sandji.sphone.mod.client.gui.phone.apps.note.GuiNote;
import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Conversation;
import fr.sandji.sphone.mod.common.phone.Message;
import fr.sandji.sphone.mod.common.phone.Note;
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
                new GuiNote(Collections.singletonList(new Note("Hello", "Coucou", System.currentTimeMillis())), new Note("Hello", "Coucou", System.currentTimeMillis())),
                new ResourceLocation(SPhone.MOD_ID ,"textures/ui/icons/notes.png"),
                "Notes",
                "1.0",
                false,
                true
        ));

        Apps.add(new App(
                new GuiConvList(Collections.singletonList(new Conversation(Collections.singletonList(new Message()), System.currentTimeMillis(), new Contact(), new Message()))),
                new ResourceLocation(SPhone.MOD_ID ,"textures/ui/icons/message.png"),
                "Messages",
                "1.0",
                false,
                true
        ));

        Apps.add(new App(
                new GuiConvList(Collections.singletonList(new Conversation(Collections.singletonList(new Message()), System.currentTimeMillis(), new Contact(), new Message()))),
                new ResourceLocation(SPhone.MOD_ID ,"textures/ui/icons/message.png"),
                "Messages",
                "1.0",
                false,
                true
        ));



        for (int i = 0; i < 10; i++) {
            Apps.add(new App(
                    new GuiConvList(Collections.singletonList(new Conversation(Collections.singletonList(new Message()), System.currentTimeMillis(), new Contact(), new Message()))),
                    new ResourceLocation(SPhone.MOD_ID ,"textures/ui/icons/message.png"),
                    "Messages",
                    "1.0",
                    false,
                    false
            ));
        }



    }

    public static void reloadApps() {
        Apps.clear();
        init();
    }


    public static List<App> getApps() {
        return Apps;
    }

    public static class App {
        GuiFrame gui;
        ResourceLocation icon;
        String name;
        String version; // don't care it's just for "realism"
        Boolean showInDebug = false; // only for dev
        Boolean defaultInAppBar = false;
        /**
         * @param gui Gui to display
         * @param icon Icon to display
         * @param name Name of the app
         * @param version Version of the app (don't care it's just for "realism")
         * @param showInDebug If true, the app will be displayed in debug mode
         * @param defaultInAppBar If true, the app will be displayed in the app bar
         * */
        public App(GuiFrame gui, ResourceLocation icon, String name, String version, Boolean showInDebug, Boolean defaultInAppBar) {
            this.gui = gui;
            this.icon = icon;
            this.name = name;
            this.version = version;
            this.showInDebug = showInDebug;
            this.defaultInAppBar = defaultInAppBar;
        }




        public GuiFrame getGui() {
            return gui;
        }

        public void setGui(GuiFrame gui) {
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
