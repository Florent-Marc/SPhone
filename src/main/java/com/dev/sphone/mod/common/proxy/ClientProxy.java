package com.dev.sphone.mod.common.proxy;

import com.dev.sphone.mod.client.SPhoneKeys;
import com.dev.sphone.mod.client.gui.phone.apps.GuiRadio;
import com.dev.sphone.mod.utils.UtilsServer;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import com.dev.sphone.mod.client.gui.phone.apps.GuiRadio;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ItemsRegister.INSTANCE);
        ClientRegistry.registerKeyBinding(SPhoneKeys.DEBUG);
        ClientRegistry.registerKeyBinding(SPhoneKeys.DEBUG_TWO);
        ClientRegistry.registerKeyBinding(SPhoneKeys.TOGGLE_GROUP_MUTE);
        UtilsServer.registerAllCssFiles();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
public void openRadioGui() {
    net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(
        new com.dev.sphone.mod.client.gui.phone.apps.GuiRadio().getGuiScreen()
    );
}
}