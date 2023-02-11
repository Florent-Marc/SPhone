package fr.sandji.sphone;

import fr.sandji.sphone.client.handlers.ClientEventHandler;
import fr.sandji.sphone.common.proxy.ClientProxy;
import fr.sandji.sphone.common.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = SPhone.MOD_ID,
        name = SPhone.MOD_NAME,
        version = SPhone.VERSION
)

public class SPhone {

    public static final String MOD_ID = "sphone";
    public static final String MOD_NAME = "SPhone";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "fr.sandji.sphone.common.proxy.ClientProxy", serverSide = "fr.sandji.sphone.common.proxy.CommonProxy")
    public static CommonProxy PROXY;
    ClientProxy clientProxy = (ClientProxy) PROXY;

    @Mod.Instance(MOD_ID)
    public static SPhone INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        PROXY.preInit();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }
}
