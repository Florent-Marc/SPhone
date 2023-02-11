package fr.sandji.sphone.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

public class GuiUtils {

    public static void drawItemImage(ItemStack is, int xPos, int yPos, float spin) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(xPos, yPos, 300.0F);
        GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(15.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(195.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(spin++ % 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(100.0F, 100.0F, 100.0F);
        RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItem(is, ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.popMatrix();
    }

}
