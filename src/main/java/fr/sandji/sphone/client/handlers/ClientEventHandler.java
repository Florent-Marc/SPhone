package fr.sandji.sphone.client.handlers;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.client.phone.HomePage;
import fr.sandji.sphone.client.phone.apps.settings.PhoneData;
import fr.sandji.sphone.client.phone.apps.settings.SettingsPage;
import fr.sandji.sphone.client.util.SPhoneKeys;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {
    Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.Key_Open_Phone.isPressed()) {
            if (PhoneData.OpenOnLastApp) {
                if (PhoneData.LastApp == "SettingsPage") {
                    ACsGuiApi.asyncLoadThenShowGui(PhoneData.LastApp, SettingsPage::new);
                }
            } else {
                ACsGuiApi.asyncLoadThenShowGui("HomePage", HomePage::new);
            }
        }
    }

}