package com.dev.sphone.mod.client.gui.phone.apps.camera;

import com.dev.sphone.api.loaders.AppDetails;
import com.dev.sphone.api.loaders.AppType;
import com.dev.sphone.mod.client.gui.phone.GuiBase;
import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.component.layout.GridLayout;
import fr.aym.acsguis.component.panel.GuiPanel;
import fr.aym.acsguis.component.panel.GuiScrollPane;
import fr.aym.acsguis.component.textarea.GuiLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@AppDetails(type = AppType.DEFAULT)
public class GuiGallery extends GuiBase {

    List<Integer> glIds = new ArrayList<>();
    public GuiGallery(GuiScreen parent) {
        super(parent);
    }

    public GuiGallery() {
        super(new GuiHome().getGuiScreen());
    }

    public List<GuiPanel> loadPage(int page) {
        int size = UtilsClient.getAllPhoneScreenshots().length;

        File[] files = UtilsClient.getAllPhoneScreenshots();
        List<GuiPanel> panels = new ArrayList<>();
        for (int j = page * 16; j < page * 16 + 16; j++) {
            if (j >= size) {
                break;
            }
            DynamicTexture texture = new DynamicTexture(getImage(files[j]).join());
            glIds.add(texture.getGlTextureId());

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
            int finalJ = j; // multiply by size ? Or 16 ?
            screen.addClickListener((mouseX, mouseY, mouseButton) -> {
                Minecraft.getMinecraft().displayGuiScreen(new GuiShowImage(finalJ).getGuiScreen());
            });

            panels.add(screen);
        }
        return panels;
    }

    @Override
    public void GuiInit() {
        super.GuiInit();

        add(this.getRoot());

        GuiScrollPane screens_list = new GuiScrollPane();
        screens_list.setCssClass("screens_list");
        screens_list.setLayout(new GridLayout(73, 120, 2, GridLayout.GridDirection.HORIZONTAL, 4));
        int size = UtilsClient.getAllPhoneScreenshots().length;
        GuiLabel pagedisplay = new GuiLabel("1/" + (int) Math.ceil(size / 16.0));
        pagedisplay.setCssClass("page_display");

        int pages = (int) Math.ceil(size / 16.0);
        final Integer[] selIndex = {0};
        screens_list.removeAllChilds();
        loadPage(0).forEach(screens_list::add);

        getRoot().addKeyboardListener((c, key) -> {
            screens_list.removeAllChilds();
            glIds.forEach(TextureUtil::deleteTexture); // avoid memory leaks, DO IT BEFORE LOADPAGE
            loadPage(selIndex[0]).forEach(screens_list::add);
            System.out.println(key);
            if(key == 203){ // left arrow
                selIndex[0]--;
                if(selIndex[0] < 0){
                    selIndex[0] = 0;
                }
            }else if(key == 205){ // right arrow
                selIndex[0]++;
                if(selIndex[0] >= pages){
                    selIndex[0] = pages - 1;
                }
            }
            pagedisplay.setText((selIndex[0] + 1) + "/" + pages);
        });

        getRoot().add(screens_list);
        getRoot().add(pagedisplay);


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
        glIds.forEach(TextureUtil::deleteTexture); // avoid memory leaks
        super.guiClose();
    }

    public List<ResourceLocation> getCssStyles() {
        List<ResourceLocation> styles = new ArrayList<>();
        styles.add(super.getCssStyles().get(0));
        styles.add(new ResourceLocation("sphone:css/gallery.css"));
        return styles;
    }

}
