package com.dev.sphone.mod.client.radio;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.server.radio.PacketRadioMuteToggle;
import com.dev.sphone.mod.common.register.SoundRegister;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * Handles the key binding used to mute/unmute the radio microphone.
 */
public class RadioKeyHandler {

    private static final String CATEGORY = "SPhone";
    public static final KeyBinding RADIO_MUTE_KEY = new KeyBinding("Mute do Rádio", Keyboard.KEY_M, CATEGORY);

    private static boolean muted = false;

    public static void init() {
        ClientRegistry.registerKeyBinding(RADIO_MUTE_KEY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new RadioKeyHandler());
    }

    public static boolean isMuted() {
        return muted;
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (RADIO_MUTE_KEY.isPressed()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player == null) {
                return;
            }
            boolean holdingRadio =
                    (!mc.player.getHeldItemMainhand().isEmpty() &&
                            mc.player.getHeldItemMainhand().getItem() == ItemsRegister.RADIO) ||
                    (!mc.player.getHeldItemOffhand().isEmpty() &&
                            mc.player.getHeldItemOffhand().getItem() == ItemsRegister.RADIO);
            if (!holdingRadio) {
                return;
            }
            muted = !muted;
            if (muted) {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundRegister.RADIO_OFF, 1.0F));
            } else {
                mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundRegister.RADIO_ON, 1.0F));
            }
            SPhone.network.sendToServer(new PacketRadioMuteToggle(muted));
        }
    }
}