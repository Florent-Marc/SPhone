package com.dev.sphone.mod.common.register;

import com.dev.sphone.SPhone;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundRegister {
    public static SoundEvent COMPOSITION;
    public static SoundEvent NONATTRIB;
    public static SoundEvent UNJOINABLE;
    public static SoundEvent RINGTONE;
    public static SoundEvent NOTIF;
    public static SoundEvent CALL;
    public static void registerSounds() {
        COMPOSITION = registerSound("composition");
        NONATTRIB = registerSound("nonattrib");
        UNJOINABLE = registerSound("unjoinable");
        RINGTONE = registerSound("ringtone");
        CALL = registerSound("call");
    }

    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(SPhone.MOD_ID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }
}
