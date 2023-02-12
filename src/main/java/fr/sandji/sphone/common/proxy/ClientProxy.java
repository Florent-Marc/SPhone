package fr.sandji.sphone.common.proxy;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.client.util.SPhoneKeys;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import static fr.sandji.sphone.client.util.SPhoneKeys.*;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        ClientRegistry.registerKeyBinding(Key_Open_Phone);

        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/home.css"));
        ACsGuiApi.registerStyleSheetToPreload(new ResourceLocation(SPhone.MOD_ID, "css/settings.css"));

    }

    @Override
    public void init() {
        super.init();
    }

}
