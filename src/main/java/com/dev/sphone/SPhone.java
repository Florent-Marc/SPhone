package com.dev.sphone;

import com.dev.sphone.mod.client.ClientEventHandler;
import com.dev.sphone.mod.client.SPhoneTab;
import com.dev.sphone.mod.common.animations.RenderAnimations;
import com.dev.sphone.mod.common.packets.Network;
import com.dev.sphone.mod.common.proxy.CommonProxy;
import com.dev.sphone.mod.common.register.RegisterHandler;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.server.commands.CommandGivePhone;
import com.dev.sphone.mod.server.commands.CommandGroup;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

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
        MinecraftForge.EVENT_BUS.register(this);
        logger = e.getModLog();

        if (e.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
            MinecraftForge.EVENT_BUS.register(new RenderAnimations());
        }

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        PROXY.init();
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandGivePhone());
        e.registerServerCommand(new CommandGroup());
        if (e.getSide().isServer()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            MethodesBDDImpl.checkFile();
            MethodesBDDImpl.checkTable();
        }
    }


}
