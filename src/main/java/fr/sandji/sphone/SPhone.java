/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone;

import fr.sandji.sphone.api.database.DatabaseManager;
import fr.sandji.sphone.mod.client.SPhoneTab;
import fr.sandji.sphone.mod.common.proxy.CommonProxy;
import fr.sandji.sphone.mod.common.register.RegisterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = SPhone.MOD_ID,
        name = SPhone.MOD_NAME,
        version = SPhone.VERSION
)

public class SPhone {

    public static final String MOD_ID = "sphone";
    public static final String MOD_NAME = "SPhone";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "fr.sandji.sphone.mod.common.proxy.ClientProxy", serverSide = "fr.sandji.sphone.mod.common.proxy.CommonProxy")
    public static CommonProxy PROXY;

    public static final CreativeTabs SPHONE_TAB = new SPhoneTab("tab");

    @Mod.Instance(MOD_ID)
    public static SPhone INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent e) {
        PROXY.preInit();
        MinecraftForge.EVENT_BUS.register(new RegisterHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        PROXY.init();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent e){
        if (e.getSide().isServer()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            DatabaseManager.initAllDatabaseConnections();
        }
    }

    @Mod.EventHandler
    public void onServerStop(FMLServerStoppingEvent e) {
        if (e.getSide().isServer()) {
            DatabaseManager.closeAllDatabaseConnections();
        }
    }

}
