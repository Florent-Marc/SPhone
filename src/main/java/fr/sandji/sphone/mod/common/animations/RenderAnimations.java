
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.animations;

import com.mrcrayfish.obfuscate.client.event.ModelPlayerEvent;
import net.minecraft.client.model.ModelPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderAnimations {

    public static boolean debug_anim;

    @SubscribeEvent
    public void onSetupAngles(ModelPlayerEvent.SetupAngles.Post e)
    {
        ModelPlayer model = e.getModelPlayer();

        if(debug_anim) {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(27F);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(27F);
            model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-10F);
            model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(10F);
        }

    }

}
