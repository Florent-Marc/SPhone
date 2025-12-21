package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.utils.ComponentRenderContext;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.HttpUtil;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class GuiPhotoElement extends GuiPanel {
    int fileid;
    UtilsClient.InternalDynamicTexture texture;

    public GuiPhotoElement(int fileid) {
        this.fileid = fileid;
        File[] files = UtilsClient.getAllPhoneScreenshots();
        if (fileid >= 0 && fileid < files.length) {
            this.texture = new UtilsClient.InternalDynamicTexture(getImage(files[fileid]).join());
        }
    }

    public GuiPhotoElement(String photo) {
        if (Objects.equals(photo, "empty")) {
            this.fileid = -1;
        } else {
            this.fileid = Integer.parseInt(photo);
            File[] files = UtilsClient.getAllPhoneScreenshots();
            if (fileid >= 0 && fileid < files.length) {
                this.texture = new UtilsClient.InternalDynamicTexture(getImage(files[fileid]).join());
            }
        }
    }

    public int getFileid() {
        return fileid;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float partialTicks, ComponentRenderContext context) {
        super.drawBackground(mouseX, mouseY, partialTicks, context);
        if (fileid == -1 || texture == null) return;

        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();

        int x = (int) (getScreenX() + getWidth() / 2f);
        int y = (int) (getScreenY() + getHeight() / 2f);

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
}
