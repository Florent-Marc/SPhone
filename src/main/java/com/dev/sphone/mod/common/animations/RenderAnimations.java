package com.dev.sphone.mod.common.animations;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import com.mrcrayfish.obfuscate.client.event.RenderItemEvent;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderAnimations {

    public static boolean debug_anim;

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post e)
    {
        ModelPlayer model = e.getModelPlayer();

        if(debug_anim) {
            model.bipedRightArm.rotationPointY += 2F;
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-170F);
            model.bipedRightArm.rotateAngleY = (float) Math.toRadians(5F);
        }
        //Take Photo : model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-80F);
    }

    // TODO : Modify the Phone Renderer for adjust with player animation.

}
