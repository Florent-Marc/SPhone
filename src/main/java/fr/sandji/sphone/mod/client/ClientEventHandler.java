package fr.sandji.sphone.mod.client;

import fr.sandji.sphone.mod.client.gui.phone.GuiHome;
import fr.sandji.sphone.mod.common.animations.RenderAnimations;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.DEBUG.isPressed()) {
            RenderAnimations.debug_anim = !RenderAnimations.debug_anim;
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit",new GuiContactsList(test);
        }
        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit", GuiHome::new);
        }
    }

    //player.world.getWorldInfo().getRainTime() / 20
    //player.world.getWorldInfo().getThunderTime() / 20
    //player.world.getWorldInfo().getCleanWeatherTime() / 20
    //make a function to get the current weather and the time before the next weather change
    //make a function to get the current time and the time before the next day
    //make a function to get the current time and the time before the next night
    //make a function to get the current time and the time before the next sunrise
    //make a function to get the current time and the time before the next sunset


}
