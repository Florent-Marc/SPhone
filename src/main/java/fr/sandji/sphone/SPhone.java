/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone;

import fr.sandji.sphone.api.database.DatabaseManager;
import fr.sandji.sphone.mod.Test;
import fr.sandji.sphone.mod.client.ClientEventHandler;
import fr.sandji.sphone.mod.client.SPhoneTab;
import fr.sandji.sphone.mod.common.animations.RenderAnimations;
import fr.sandji.sphone.mod.common.packets.Network;
import fr.sandji.sphone.mod.common.proxy.CommonProxy;
import fr.sandji.sphone.mod.common.register.RegisterHandler;
import fr.sandji.sphone.mod.server.ServerEventHandler;
import fr.sandji.sphone.mod.server.commands.CommandGivePhone;
import fr.sandji.sphone.mod.server.commands.CommandGroup;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(
        modid = SPhone.MOD_ID,
        name = SPhone.MOD_NAME,
        version = SPhone.VERSION,
        dependencies = "after: voicechat"
)

public class SPhone {

    public static final String MOD_ID = "sphone";
    public static final String MOD_NAME = "SPhone";
    public static final String VERSION = "0.0.1";
    public static final boolean DEV_MOD = true;

    @SidedProxy(clientSide = "fr.sandji.sphone.mod.common.proxy.ClientProxy", serverSide = "fr.sandji.sphone.mod.common.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static final CreativeTabs SPHONE_TAB = new SPhoneTab("tab");

    @Mod.Instance(MOD_ID)
    public static SPhone INSTANCE;
    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        PROXY.preInit();
        Network.init();
        MinecraftForge.EVENT_BUS.register(new RegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);

        if (e.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
            MinecraftForge.EVENT_BUS.register(new RenderAnimations());
        }
        if (e.getSide().isServer()) {
            MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        PROXY.init();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent e){
        e.registerServerCommand(new CommandGivePhone());
        e.registerServerCommand(new CommandGroup());
        if (e.getSide().isServer()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            DatabaseManager.initAllDatabaseConnections();
            Test.Test();
        }
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppingEvent e) {
        if (e.getSide().isServer()) {
            DatabaseManager.closeAllDatabaseConnections();
        }
    }

}
