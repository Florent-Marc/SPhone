/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.proxy;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.SPhone;
import fr.sandji.sphone.mod.client.SPhoneKeys;
import fr.sandji.sphone.mod.common.register.ItemsRegister;
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
    }

    @Override
    public void init() {
        super.init();
    }

}
