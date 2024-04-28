package com.dev.sphone.mod.client.gui.phone.apps.settings;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.phone.sim.SIMContainer;
import com.dev.sphone.mod.common.phone.sim.SIMInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiSimEditor extends GuiContainer {
    public static final ResourceLocation texture = new ResourceLocation(SPhone.MOD_ID, "textures/ui/background/sim.png");
    protected SIMInventory inv;
    protected InventoryPlayer playerInv;
    public int rows;

    public GuiSimEditor(InventoryPlayer playerInv, SIMInventory inv) {
        super(new SIMContainer(playerInv, inv));
        this.playerInv = playerInv;
        this.inv = inv;
        this.allowUserInput = false;
        // Calculate the number of rows

        // Height of the GUI using the number of rows
        this.ySize = 256;
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(I18n.format("sphone.sim.title"), 8, 6, 4210752);
        this.fontRenderer.drawString(this.playerInv.hasCustomName() ? this.playerInv.getName() : I18n.format(this.playerInv.getName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float prt, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);

        // Centering GUI
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 4;
        // And after the slots from the player's inventory
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 256);
    }
}