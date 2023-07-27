package com.dev.sphone.mod.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.animations.RenderAnimations;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class ClientEventHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();

    /*
    @SubscribeEvent

    public void addApp(InitAppEvent event){
        event.getApps().add(new AppManager.App(() -> new GuiCalculator(new GuiHome().getGuiScreen()).getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/calculator.png"),
                "Calculatrice",
                "1.0",
                false,
                false,
                null
        ));
    }
     */


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
