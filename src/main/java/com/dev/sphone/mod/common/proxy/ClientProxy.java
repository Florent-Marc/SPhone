/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.common.proxy;

import com.dev.sphone.mod.client.SPhoneKeys;
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
    }

    @Override
    public void init() {
        super.init();
    }

}
