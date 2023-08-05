package com.dev.sphone.mod.common.proxy;

import com.dev.sphone.mod.client.SPhoneKeys;
import com.dev.sphone.mod.utils.Utils;
import fr.aym.acsguis.api.ACsGuiApi;
import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ItemsRegister.INSTANCE);
        ClientRegistry.registerKeyBinding(SPhoneKeys.DEBUG);
        ClientRegistry.registerKeyBinding(SPhoneKeys.DEBUG_TWO);
        Utils.registerAllCssFiles();
    }

    @Override
    public void init() {
        super.init();
    }

}
