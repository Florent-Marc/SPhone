package com.dev.sphone;

import com.dev.sphone.api.events.DatabaseBuildEvent;
import com.dev.sphone.api.voicemanager.VoiceManager;
import com.dev.sphone.api.voicemanager.voicechat.VoiceNetwork;
import com.dev.sphone.mod.client.ClientEventAnim;
import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.SPhoneTab;
import com.dev.sphone.mod.client.gui.phone.NotificationManager;
import com.dev.sphone.mod.common.GuiHandler;
import com.dev.sphone.mod.common.packets.Network;
import com.dev.sphone.mod.common.proxy.CommonProxy;
import com.dev.sphone.mod.common.register.RegisterHandler;
import com.dev.sphone.mod.common.register.SoundRegister;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.server.bdd.sql.MySQL;
import com.dev.sphone.mod.server.commands.CommandGivePhone;
import com.dev.sphone.mod.utils.ObfuscateUtils;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;
import fr.aym.acsguis.api.ACsGuiApi;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = SPhone.MOD_ID,
        name = SPhone.MOD_NAME,
        version = SPhone.VERSION,
        dependencies = "after: voicechat; required-after: voicechat@[1.12.2-2.4.13,); required-after: acslib@[1.2.2,);"
)

public class SPhone {

    public static final String MOD_ID = "sphone";
    public static final String MOD_NAME = "SPhone";
    public static final String VERSION = "0.0.1";
    public static final boolean DEV_MOD = false;

    @SidedProxy(clientSide = "com.dev.sphone.mod.common.proxy.ClientProxy", serverSide = "com.dev.sphone.mod.common.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static final CreativeTabs SPHONE_TAB = new SPhoneTab("tab");

    @Mod.Instance(MOD_ID)
    public static SPhone INSTANCE;
    public static SimpleNetworkWrapper network;
    public static Logger logger;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        PROXY.preInit();
        Network.init();

        MinecraftForge.EVENT_BUS.register(new RegisterHandler());
        MinecraftForge.EVENT_BUS.register(new VoiceNetwork());
        MinecraftForge.EVENT_BUS.register(this);
        logger = e.getModLog();
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
        if (e.getSide().isClient()) {
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/base.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/home.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/contactslist.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/convlist.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/contacts.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/calculator.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/call.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/newcontact.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/conv.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/callrequest.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/weather.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/notelist.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/note.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/newnote.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/gallery.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/settings.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/nosim.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/appstore.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/plusplusgame.css"));
            ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/twitter.css"));
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());

            if (isUsingMod("com.mrcrayfish.obfuscate.Obfuscate"))
                MinecraftForge.EVENT_BUS.register(new ClientEventAnim());
            NotificationManager.init();
        }
        SoundRegister.registerSounds();
        VoiceManager.init(e.getSide());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setupDB(DatabaseBuildEvent.Pre e) {
        e.addDatabaseType("mysql", MySQL.class);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        PROXY.init();
        if (isUsingMod("com.mrcrayfish.obfuscate.Obfuscate"))
            ObfuscateUtils.init();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent e) throws DatabaseException {
        e.registerServerCommand(new CommandGivePhone());
        MethodesBDDImpl.checkFile();

        MethodesBDDImpl.init();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean isUsingMod(String mainClass) {
        try {
            Class.forName(mainClass);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
