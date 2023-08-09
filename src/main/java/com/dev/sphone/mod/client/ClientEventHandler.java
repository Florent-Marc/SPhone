package com.dev.sphone.mod.client;

import com.dev.sphone.mod.client.gui.phone.GuiHome;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.utils.Utils;
import com.dev.sphone.mod.utils.UtilsClient;
import fr.aym.acsguis.api.ACsGuiApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

public class ClientEventHandler {

    public static final Minecraft mc = Minecraft.getMinecraft();

    /*
    @SubscribeEvent

    public void addApp(InitAppEvent event){
        event.getApps().add(new AppManager.App(() -> new GuiCalculator(new GuiHome().getGuiScreen()).getGuiScreen(),
                new ResourceLocation(SPhone.MOD_ID, "textures/ui/icons/calculator.png"),
                "Calculatrice",
                "1.0",
                false,
                false,
                null
        ));
    }
     */


    public static boolean isCameraActive = false;

    private static final ResourceLocation CAMERA_OVERLAY = new ResourceLocation("SPhone", "textures/ui/background/appcam.png");
    private static int framebufferTextureId = -1;

    public static DynamicTexture lastPhoneScreenshot;

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {


            if(isCameraActive) {
                if(!(mc.player.getHeldItemMainhand().getItem() instanceof ItemPhone)){
                    UtilsClient.leaveCamera(false);
                    return;
                }

                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int screenWidth = scaledResolution.getScaledWidth();
                int screenHeight = scaledResolution.getScaledHeight();

                if (framebufferTextureId == -1) {
                    framebufferTextureId = GL11.glGenTextures();
                }

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextureId);
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, mc.displayWidth / 3, mc.displayHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextureId);
                GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, mc.displayWidth / 3, 0, mc.displayWidth / 3, mc.displayHeight);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

                GlStateManager.pushMatrix();
                mc.getTextureManager().bindTexture(CAMERA_OVERLAY);
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.22f, screenHeight / 0.74f - screenHeight, 0.0F);
                GL11.glTexCoord2f(0.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.22f, screenHeight / 1.026f, 0.0F);
                GL11.glTexCoord2f(1.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.0087f, screenHeight / 1.026f, 0.0F);
                GL11.glTexCoord2f(1.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.0087f, screenHeight / 0.74f - screenHeight, 0.0F);
                GL11.glEnd();
                GlStateManager.popMatrix();

                GlStateManager.pushMatrix();
                GlStateManager.scale(1, 0.92, 1);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, framebufferTextureId);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);
                GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.2075f, screenHeight / 0.695f - screenHeight, 0.0F);
                GL11.glTexCoord2f(0.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.2075f, screenHeight / 1.1f, 0.0F);
                GL11.glTexCoord2f(1.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.0175f, screenHeight / 1.1f, 0.0F);
                GL11.glTexCoord2f(1.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.0175f, screenHeight / 0.695f - screenHeight, 0.0F);
                GL11.glEnd();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();

                if (lastPhoneScreenshot == null) {
                    CompletableFuture<BufferedImage> lastPhoneImage = UtilsClient.getLastPhoneImage();
                    if (lastPhoneImage == null) {
                        return;
                    }
                    lastPhoneScreenshot = new DynamicTexture(lastPhoneImage.join());
                }

                GlStateManager.pushMatrix();
                GlStateManager.bindTexture(lastPhoneScreenshot.getGlTextureId());
                GlStateManager.translate(3.5f, 3, 0);
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.2f, screenHeight / 1.13f, 0.0F);
                GL11.glTexCoord2f(0.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.2f, screenHeight / 1.075f, 0.0F);

                GL11.glTexCoord2f(1.0F, 1.0F);
                GL11.glVertex3f(screenWidth / 1.165f, screenHeight / 1.075f, 0.0F);
                GL11.glTexCoord2f(1.0F, 0.0F);
                GL11.glVertex3f(screenWidth / 1.165f, screenHeight / 1.13f, 0.0F);
                GL11.glEnd();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
                GlStateManager.popMatrix();
            }

        }
    }


    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {

        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            ACsGuiApi.asyncLoadThenShowGui("GuiHome", GuiHome::new);
        }

        if(isCameraActive){
            if (Keyboard.getEventKeyState()) {
                int keycode = Keyboard.getEventKey();
                if (keycode == Keyboard.KEY_BACK || keycode == Keyboard.KEY_DELETE || keycode == Keyboard.KEY_ESCAPE || keycode == Keyboard.KEY_E) {
                    UtilsClient.leaveCamera(true);
                }
                if (keycode == Keyboard.KEY_SPACE || keycode == Keyboard.KEY_RETURN) {
                    UtilsClient.makeScreenPhone(framebufferTextureId);
                }
            }
        }
    }
}
