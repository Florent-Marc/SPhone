package com.dev.sphone.mod.utils;

import com.dev.sphone.SPhone;
import com.mrcrayfish.obfuscate.common.data.Serializers;
import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.minecraft.util.ResourceLocation;

public class ObfuscateUtils {

    public static void init(){
        SyncedPlayerData.instance().registerKey(PHOTO_MODE);
    }

    public static final SyncedDataKey<Boolean> PHOTO_MODE = SyncedDataKey.builder(Serializers.BOOLEAN)
            .id(new ResourceLocation(SPhone.MOD_ID, "photo_mode"))
            .defaultValueSupplier(() -> false)
            .resetOnDeath()
            .build();
}
