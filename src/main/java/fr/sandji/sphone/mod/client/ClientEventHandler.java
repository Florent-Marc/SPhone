/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.client;


import fr.aym.acsguis.api.ACsGuiApi;
import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.animations.RenderAnimations;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.DEBUG.isPressed()) {
            RenderAnimations.debug_anim = !RenderAnimations.debug_anim;
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit",new GuiContactsList(test));
        }
        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit", GuiHome::new);
        }
    }
}
