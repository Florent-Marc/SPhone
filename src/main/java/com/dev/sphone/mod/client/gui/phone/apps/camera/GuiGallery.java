package com.dev.sphone.mod.client.gui.phone.apps.camera;

import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.utils.Utils;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GuiGallery extends GuiBase {

    public GuiGallery(GuiScreen parent) {
        super(parent);
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(this.getRoot());

        GuiScrollPane screens_list = new GuiScrollPane();
        screens_list.setCssClass("screens_list");
        screens_list.setLayout(new GridLayout(73, 120, 2, GridLayout.GridDirection.HORIZONTAL, 4));

        for(File file : UtilsClient.getAllPhoneScreenshots()){
            DynamicTexture texture = new DynamicTexture(getImage(file).join());
            GuiPanel screen = new GuiPanel(){
                @Override
                public void drawBackground(int mouseX, int mouseY, float partialTicks) {
                    super.drawBackground(mouseX, mouseY, partialTicks);
                    ScaledResolution scaledResolution = new ScaledResolution(mc);
                    int screenWidth = scaledResolution.getScaledWidth();
                    int screenHeight = scaledResolution.getScaledHeight();

                    int x = getScreenX() + getWidth() / 2;
                    int y = getScreenY() + getHeight() / 2;

                    GlStateManager.pushMatrix();
                    GlStateManager.bindTexture(texture.getGlTextureId());
                    GlStateManager.translate(x, y, 0);
                    GlStateManager.scale(0.077f, 0.235f, 0.3f);
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0.0F, 0.0F);
                    GL11.glVertex3f(-screenWidth, -screenHeight, 0.0F);
                    GL11.glTexCoord2f(0.0F, 1.0F);
                    GL11.glVertex3f(-screenWidth, screenHeight, 0.0F);

                    GL11.glTexCoord2f(1.0F, 1.0F);
                    GL11.glVertex3f(screenWidth, screenHeight, 0.0F);
                    GL11.glTexCoord2f(1.0F, 0.0F);
                    GL11.glVertex3f(screenWidth, -screenHeight, 0.0F);
                    GL11.glEnd();
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                    GlStateManager.popMatrix();
                }
            };
            screen.setCssClass("screen");

            screens_list.add(screen);
        }
        getRoot().add(screens_list);
    }

    private static CompletableFuture<BufferedImage> getImage(File file) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return ImageIO.read(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, HttpUtil.DOWNLOADER_EXECUTOR);
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/gallery.css"));
        return styles;
    }

}
