package com.dev.sphone.mod.client;

import com.jme3.math.Vector3f;
import fr.dynamx.utils.client.DynamXRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Point> test = new ArrayList<>();
    public static List<Vector3f> test2 = new ArrayList<>();
    public static int index = 0;


    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent event) {
        if (SPhoneKeys.DEBUG.isPressed()) {
            //RenderAnimations.debug_anim = !RenderAnimations.debug_anim;
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit",new GuiContactsList(test));
            //IProfils capa = Minecraft.getMinecraft().player.getCapability(CapabilityHandler.Profils_CAPABILITY, null);
            EntityPlayer player = Minecraft.getMinecraft().player;
            Vector3f pos = new Vector3f((float) player.getPositionVector().x, (float) player.getPositionVector().y, (float) player.getPositionVector().z);
            test.add(new Point(pos));
            test2.add(pos);
            player.sendMessage(new TextComponentString("Point added !"));


        }
        if (SPhoneKeys.DEBUG_TWO.isPressed()) {
            //Minecraft.getMinecraft().displayGuiScreen(new GuiHome().getGuiScreen());
            //ACsGuiApi.asyncLoadThenShowGui("GuiInit", GuiHome::new);
            //fait avancer la cible du point 0 a 1
            if (index < test2.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        if (test.isEmpty()) {
            return;
        }
        if (mc.world != null) {
            GlStateManager.pushMatrix();

            Vector3f lastPos = null;

            Entity entity = mc.getRenderViewEntity();
            double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) event.getPartialTicks();
            double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) event.getPartialTicks();
            double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) event.getPartialTicks();
            GlStateManager.translate(-d0, -d1, -d2);

            GlStateManager.disableLighting();
            GlStateManager.disableColorMaterial();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);

            for (Point pos : test) {
                if (lastPos == null) {
                    lastPos = new Vector3f(pos.getPos());
                    if (test.indexOf(pos) == index) {
                        DynamXRenderUtils.drawSphere(lastPos, 0.5f, 10, Color.CYAN);
                    } else {
                        DynamXRenderUtils.drawSphere(lastPos, 0.5f, 10, Color.GREEN);
                    }
                } else {

                    drawLine(lastPos, pos.getPos(), Color.red);

                    lastPos = new Vector3f(pos.getPos());
                    if (test.indexOf(pos) == index) {
                        DynamXRenderUtils.drawSphere(lastPos, 0.5f, 10, Color.CYAN);
                    } else if (test.indexOf(pos) == test.size() - 1) {
                        DynamXRenderUtils.drawSphere(new Vector3f(pos.getPos()), 0.5f, 10, Color.red);
                    } else {
                        DynamXRenderUtils.drawSphere(new Vector3f(pos.getPos()), 0.3f, 10, Color.blue);
                    }
                }

                drawText("Point " + test.indexOf(pos), pos.getPos(), Color.white);

            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
    }

    public void drawLine(Vector3f start, Vector3f end, Color color) {
        GlStateManager.pushMatrix();
        GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.glBegin(GL11.GL_LINES);
        GL11.glVertex3d(start.x, start.y, start.z);
        GL11.glVertex3d(end.x, end.y, end.z);
        GlStateManager.glEnd();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }

    public void drawText(String text, Vector3f pos, Color color) {
        GlStateManager.pushMatrix();
        FontRenderer fontRenderer = mc.fontRenderer;
        GlStateManager.translate(pos.x, pos.y + 1, pos.z);
        GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.scale(-0.025F, -0.025F, 0.025F);
        int i = fontRenderer.getStringWidth(text) / 2;
        GlStateManager.enableTexture2D();
        fontRenderer.drawString(text, -i, 0, color.getRGB());
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.popMatrix();
    }

}
