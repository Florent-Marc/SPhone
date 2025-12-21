package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.InitAppEvent;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.gui.phone.apps.DevGui;
import com.dev.sphone.mod.client.gui.phone.apps.appstore.GuiAppStoreHome;
import com.dev.sphone.mod.client.gui.phone.apps.calculator.GuiCalculator;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiMakeCall;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiGallery;
import com.dev.sphone.mod.client.gui.phone.apps.settings.GuiSettingList;
import com.dev.sphone.mod.common.packets.server.PacketRequestData;
import com.dev.sphone.mod.common.packets.server.PacketSetAnim;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
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

        MinecraftForge.EVENT_BUS.post(new InitAppEvent(apps));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/message.png"),
                "messages",
                I18n.format("sphone.message.title"),
                "System app",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("conversations")),
                true
        ));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/weather_icon.png"),
                "weather",
                I18n.format("sphone.meteo.title"),
                "System app",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("weather")),
                true
        ));

                apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/bank.png"),
                "bank",
                I18n.format("sphone.bank.title"),
                "System app",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("bank")),
                true
        ));

        guiSupplier = () -> new GuiMakeCall(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/call.png"),
                "Phone",
                I18n.format("sphone.phone.title"),
                "System app",
                "1.0",
                false,
                true,
                null,
                true
        ));


        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/notes.png"),
                "notes",
                I18n.format("sphone.notes.title"),
                "System app",
                "1.0",
                false,
                true,
                () -> SPhone.network.sendToServer(new PacketRequestData("notes")),
                true
        ));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/contacts.png"),
                "contacts",
                I18n.format("sphone.contacts.title"),
                "System app",
                "1.0",
                false,
                true,
                () -> SPhone.network.sendToServer(new PacketRequestData("contacts")),
                true
        ));

        guiSupplier = () -> new GuiCalculator(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/calculator.png"),
                "calculator",
                I18n.format("sphone.calculator.title"),
                "Simple calculator",
                "1.0",
                false,
                false,
                null,
                false
        ));

        guiSupplier = () -> new GuiSettingList(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/settings.png"),
                "settings",
                I18n.format("sphone.settings.title"),
                "System app",
                "1.0",
                false,
                false,
                null,
                true
        ));


        apps.add(new App(null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/photo.png"),
                "camera",
                I18n.format("sphone.camera.title"),
                "System app",
                "1.0",
                false,
                false,
                () -> {
                    if (SPhone.isUsingMod("com.mrcrayfish.obfuscate.Obfuscate"))
                        SPhone.network.sendToServer(new PacketSetAnim(true));
                    ClientEventHandler.isCameraActive = true;
                    ClientEventHandler.mc.player.closeScreen();
                },
                true
        ));

        guiSupplier = () -> new GuiGallery(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/gallery.png"),
                "gallery",
                I18n.format("sphone.gallery.title"),
                "System app",
                "1.0",
                false,
                false,
                null,
                true
        ));

        guiSupplier = () -> new GuiAppStoreHome(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/appstore.png"),
                "appstore",
                I18n.format("sphone.appstore.title"),
                "System app",
                "1.0",
                false,
                false,
                null,
                true
        ));

        if (GuiHome.DEVMODE_LOCAL) {
            guiSupplier = () -> new DevGui(root).getGuiScreen();

            apps.add(new App(guiSupplier,
                    new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/devapp.png"),
                    "dev",
                    I18n.format("sphone.dev.title"),
                    "dev",
                    "0.0",
                    false,
                    false,
                    null,
                    true
            ));
        }
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
        String uniqueName;
        String name;
        String description;
        String version; // don't care it's just for "realism"
        boolean showInDebug = false; // only for dev
        boolean defaultInAppBar = false;
        Runnable runnable;
        boolean isDefaultOnPhone = false;

        public App() { }

        /**
         * @param gui             Gui to display
         * @param icon            Icon to display
         * @param name            Name of the app
         * @param description     Description of the app
         * @param version         Version of the app (don't care it's just for "realism")
         * @param showInDebug     If true, the app will be displayed in debug mode
         * @param defaultInAppBar If true, the app will be displayed in the app bar
         * @param runnable        Runnable to execute when the app is opened
         * @param isDefaultOnPhone If true, the app will be displayed in the phone
         */
        public App(Supplier<GuiScreen> gui, ResourceLocation icon, String uniqueName,String name, String description, String version, boolean showInDebug, boolean defaultInAppBar, @Nullable Runnable runnable, boolean isDefaultOnPhone) {
            this.gui = gui;
            this.icon = icon;
            this.uniqueName = uniqueName;
            this.name = name;
            this.description = description;
            this.version = version;
            this.showInDebug = showInDebug;
            this.defaultInAppBar = defaultInAppBar;
            this.runnable = runnable;
            this.isDefaultOnPhone = isDefaultOnPhone;
        }

        public GuiScreen getGui() {
            if (gui != null) {
                return gui.get();
            }
            return null;
        }

        public void setGui(GuiScreen gui) {
            //this.gui.gui;
        }

        public String getUniqueName() {
            return uniqueName;
        }

        public void setUniqueName(String uniqueName) {
            this.uniqueName = uniqueName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getAppVersion() {
            return version;
        }

        public void setGui(Supplier<GuiScreen> gui) {
            this.gui = gui;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public boolean isShowInDebug() {
            return showInDebug;
        }

        public boolean isDefaultInAppBar() {
            return defaultInAppBar;
        }

        public void setRunnable(Runnable runnable) {
            this.runnable = runnable;
        }

        public boolean isDefaultOnPhone() {
            return isDefaultOnPhone;
        }

        public void setDefaultOnPhone(boolean defaultOnPhone) {
            isDefaultOnPhone = defaultOnPhone;
        }

        public void setAppVersion(String version) {
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
