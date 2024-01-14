package com.dev.sphone.mod.client.gui.phone.apps.contacts;

import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.GuiComponent;
import fr.aym.acsguis.component.panel.GuiPanel;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
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
        this.texture = new UtilsClient.InternalDynamicTexture(getImage(files[fileid]).join());
    }

    public GuiPhotoElement(String photo) {
        if(Objects.equals(photo, "empty")) {
            this.fileid = -1;
        } else {
            this.fileid = Integer.parseInt(photo);
            File[] files = UtilsClient.getAllPhoneScreenshots();
            this.texture = new UtilsClient.InternalDynamicTexture(getImage(files[fileid]).join());
        }
    }

    public int getFileid() {
        return fileid;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(mouseX, mouseY, partialTicks);
        if(fileid == -1) return;
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
