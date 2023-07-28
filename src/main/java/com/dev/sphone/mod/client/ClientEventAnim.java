package com.dev.sphone.mod.client;

import com.dev.sphone.mod.utils.ObfuscateUtils;
import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.minecraft.client.model.ModelPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventAnim {

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post e) {
        ModelPlayer model = e.getModelPlayer();
        if(SyncedPlayerData.instance().get(e.getEntityPlayer(), ObfuscateUtils.PHOTO_MODE)) {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-80F);
        }
    }

}
