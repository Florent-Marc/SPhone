package com.dev.sphone.mod.client.gui.phone;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.InitAppEvent;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.gui.phone.apps.DevGui;
import com.dev.sphone.mod.client.gui.phone.apps.appstore.GuiAppStoreHome;
import com.dev.sphone.mod.client.gui.phone.apps.calculator.GuiCalculator;
import com.dev.sphone.mod.client.gui.phone.apps.call.GuiMakeCall;
import com.dev.sphone.mod.client.gui.phone.apps.camera.GuiGallery;
import com.dev.sphone.mod.client.gui.phone.apps.plusplusgame.GuiPlusPlusGame;
import com.dev.sphone.mod.client.gui.phone.apps.settings.GuiSettingList;
import com.dev.sphone.mod.common.packets.server.PacketRequestData;
import com.dev.sphone.mod.common.packets.server.PacketSetAnim;
import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
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

        MinecraftForge.EVENT_BUS.post(new InitAppEvent(apps));

        apps.add(new App(
                null,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/message.png"),
                "messages",
                "Messages",
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
                "Weather",
                "System app",
                "1.0",
                false,
                false,
                () -> SPhone.network.sendToServer(new PacketRequestData("weather")),
                true
        ));

        guiSupplier = () -> new GuiMakeCall(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/call.png"),
                "Phone",
                "Téléphone",
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
                "Notes",
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
                "Contacts",
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
                "Calculatrice",
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
                "Paramètres",
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
                "Camera",
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
                "Galerie",
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
                "App Store",
                "System app",
                "1.0",
                false,
                false,
                null,
                true
        ));

        guiSupplier = () -> new GuiPlusPlusGame(root).getGuiScreen();

        apps.add(new App(guiSupplier,
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/plusplusgame.png"),
                "plusplusgame",
                "++ Game",
                "The famous ++ game",
                "1.0",
                false,
                false,
                null,
                false
        ));

        if (GuiHome.DEVMODE_LOCAL) {
            guiSupplier = () -> new DevGui(root).getGuiScreen();

            apps.add(new App(guiSupplier,
                    new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/devapp.png"),
                    "dev",
                    "Developement",
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

    public static class App implements ISerializable, ISerializablePacket {
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

        public App() {
        }

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

        @Override
        public int getVersion() {
            return 0;
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

        @Override
        public Object[] getObjectsToSave() {
            return new Object[]{
                    gui,
                    icon,
                    name,
                    version,
                    showInDebug,
                    defaultInAppBar,
                    runnable,
                    isDefaultOnPhone
            };
        }

        @Override
        public void populateWithSavedObjects(Object[] objects) {
            gui = (Supplier<GuiScreen>) objects[0];
            icon = (ResourceLocation) objects[1];
            name = (String) objects[2];
            version = (String) objects[3];
            showInDebug = (boolean) objects[4];
            defaultInAppBar = (boolean) objects[5];
            runnable = (Runnable) objects[6];
            isDefaultOnPhone = (boolean) objects[7];
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
