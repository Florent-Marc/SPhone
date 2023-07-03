/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client;

import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.DEBUG.isPressed()) {

            //ACsGuiApi.asyncLoadThenShowGui("GuiInit",new GuiContactsList(test));
        }
        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            ACsGuiApi.asyncLoadThenShowGui("GuiInit", GuiHome::new);
        }
    }

}
