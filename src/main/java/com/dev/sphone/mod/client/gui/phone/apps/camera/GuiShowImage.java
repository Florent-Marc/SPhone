package com.dev.sphone.mod.client.gui.phone.apps.camera;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.button.GuiButton;
import fr.aym.acsguis.component.button.GuiCheckBox;
import fr.aym.acsguis.component.button.GuiSlider;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.utils.ComponentRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

@AppDetails(type = AppType.DEFAULT)
public class GuiShowImage extends GuiBase {

    private final File imageFile;
    private Integer gid;
    private UtilsClient.InternalDynamicTexture texture;

    public GuiShowImage(File imageFile) {
        super(new GuiGallery().getGuiScreen());
        this.imageFile = imageFile;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        final float[] editR = {0};
        final float[] editG = {0};
        final float[] editB = {0};
        AtomicBoolean isBlurred = new AtomicBoolean(false);

        add(this.getRoot());

        if (imageFile == null) return;
        texture = new UtilsClient.InternalDynamicTexture(getImage(imageFile).join());
        gid = texture.getGlTextureId();

        GuiPanel screen = new GuiPanel() {
            @Override
            public void drawBackground(int mouseX, int mouseY, float partialTicks, ComponentRenderContext context) {
    super.drawBackground(mouseX, mouseY, partialTicks, context);
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                float screenWidth = scaledResolution.getScaledWidth() / 2.1f;
                float screenHeight = scaledResolution.getScaledHeight() / 1.2f;

                float x = getScreenX() + getWidth() / 2f;
                float y = getScreenY() + getHeight() / 2.15f;

                GlStateManager.pushMatrix();
                GlStateManager.bindTexture(texture.getGlTextureId());
                GlStateManager.translate(x, y, 0);
                GlStateManager.scale(0.9f, 0.9f, 0.9f);
                GlStateManager.color(1 - editR[0], 1 - editG[0], 1 - editB[0]);
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
        screen.setCssClass("screenlarge");
        getRoot().add(screen);

        GuiSlider sliderR = new GuiSlider(true);
        sliderR.setCssClass("sliderR");
        sliderR.setMin(0);
        sliderR.setMax(255);
        sliderR.setValue(0);
        sliderR.addSliderListener((var) -> {
            editR[0] = (float) (sliderR.getValue() / 255f);
        });
        getRoot().add(sliderR);

        GuiSlider sliderG = new GuiSlider(true);
        sliderG.setCssClass("sliderG");
        sliderG.setMin(0);
        sliderG.setMax(255);
        sliderG.setValue(0);
        sliderG.getStyle().setOffsetY(-10);
        sliderG.addSliderListener((var) -> {
            editG[0] = (float) (sliderG.getValue() / 255f);
        });
        getRoot().add(sliderG);

        GuiSlider sliderB = new GuiSlider(true);
        sliderB.setCssClass("sliderB");
        sliderB.setMin(0);
        sliderB.setMax(255);
        sliderB.setValue(0);
        sliderB.getStyle().setOffsetY(-20);
        sliderB.addSliderListener((var) -> {
            editB[0] = (float) (sliderB.getValue() / 255f);
        });
        getRoot().add(sliderB);

        GuiCheckBox blur = new GuiCheckBox("Blur image ?");
        blur.setCssClass("blur");
        blur.addClickListener((mouseX, mouseY, mouseButton) -> {
            isBlurred.set(!isBlurred.get());
        });
        getRoot().add(blur);

        GuiPanel delete = new GuiPanel();
        delete.setCssClass("delete");
        delete.addClickListener((mouseX, mouseY, mouseButton) -> {
            if (imageFile != null) {
                imageFile.delete();
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiGallery().getGuiScreen());
        });
        getRoot().add(delete);

        GuiButton save = new GuiButton("Save");
        save.setCssClass("save");
        save.addClickListener((mouseX, mouseY, mouseButton) -> {
            if (editR[0] == 0 && editG[0] == 0 && editB[0] == 0 && !isBlurred.get()) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiGallery().getGuiScreen());
                Minecraft.getMinecraft().player.sendMessage(new TextComponentString("No changes were made to the image."));
                return;
            }
            BufferedImage image = new BufferedImage(texture.getWidth(), texture.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < texture.getHeight(); y++) {
                for (int x = 0; x < texture.getWidth(); x++) {
                    int rgb = texture.getTextureData()[x + y * texture.getWidth()];
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = rgb & 0xFF;

                    r = (int) (r * (1 - editR[0]));
                    g = (int) (g * (1 - editG[0]));
                    b = (int) (b * (1 - editB[0]));

                    rgb = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, rgb);
                }
            }
            if (isBlurred.get()) {
                for (int i = 0; i < 5; i++) {
                    BufferedImage image2 = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                    for (int y = 0; y < image.getHeight(); y++) {
                        for (int x = 0; x < image.getWidth(); x++) {
                            int rgb;
                            int r2 = 0, g2 = 0, b2 = 0, count = 0;
                            for (int y2 = -3; y2 <= 3; y2++) {
                                for (int x2 = -3; x2 <= 3; x2++) {
                                    if (x + x2 < 0 || x + x2 >= image.getWidth() || y + y2 < 0 || y + y2 >= image.getHeight()) {
                                        continue;
                                    }
                                    int rgb2 = image.getRGB(x + x2, y + y2);
                                    r2 += (rgb2 >> 16) & 0xFF;
                                    g2 += (rgb2 >> 8) & 0xFF;
                                    b2 += rgb2 & 0xFF;
                                    count++;
                                }
                            }
                            r2 /= count;
                            g2 /= count;
                            b2 /= count;
                            rgb = (r2 << 16) | (g2 << 8) | b2;
                            image2.setRGB(x, y, rgb);
                        }
                    }
                    image = image2;
                }
            }
            File file1 = new File("phonescreenshots");
            file1.mkdir();
            File file2 = UtilsClient.getTimestampedPNGFileForDirectory(file1);
            try {
                file2 = file2.getCanonicalFile();
                ImageIO.write(image, "png", file2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().displayGuiScreen(new GuiGallery().getGuiScreen());
        });
        getRoot().add(save);
    }

    @Override
    public boolean needsCssReload() {
        return false;
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

    @Override
    public void guiClose() {
        if (gid != null)
            TextureUtil.deleteTexture(gid);
        super.guiClose();
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/gallery.css"));
        return styles;
    }
}
