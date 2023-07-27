package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.AppAddon;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiCall;
import com.dev.sphone.mod.common.packets.server.PacketRequestData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AppManager {
    private static final List<App> apps = new ArrayList<>();
    /**
     * @apiNote Add only the guis who there are to be displayed in home.
     * @author gabidut76
     */
    public static void init(GuiScreen root) {

        Supplier<GuiScreen> guiSupplier;

        MinecraftForge.EVENT_BUS.post(new AppAddon(apps));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/message.png"),
                "Messages",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("conversations"))
        ));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/weather_icon.png"),
                "Messages",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("weather"))
        ));

        guiSupplier = () -> new GuiCall(root, "t").getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/call.png"),
                "Téléphone",
                "1.0",
                false,
                true,
                null
        ));


        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/notes.png"),
                "Notes",
                "1.0",
                false,
                true,
                () -> SPhone.network.sendToServer(new PacketRequestData("notes"))
        ));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/contacts.png"),
                "Contacts",
                "1.0",
                false,
                true,
                () -> SPhone.network.sendToServer(new PacketRequestData("contacts"))
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
