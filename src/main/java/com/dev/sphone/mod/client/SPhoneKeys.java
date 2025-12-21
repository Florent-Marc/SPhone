package com.dev.sphone.mod.client;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class SPhoneKeys {

    public static final KeyBinding DEBUG = new KeyBinding("Debug", Keyboard.KEY_F6, "SPhone");
    public static final KeyBinding DEBUG_TWO = new KeyBinding("Debug 2", Keyboard.KEY_F6, "SPhone");
    
    /**
     * Keybind used to toggle the mute state of the voice chat group call.
     */
    public static final KeyBinding TOGGLE_GROUP_MUTE = new KeyBinding("Toggle Call Mute", Keyboard.KEY_M, "SPhone");

}
