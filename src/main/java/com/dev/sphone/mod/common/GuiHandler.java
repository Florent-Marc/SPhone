package com.dev.sphone.mod.common;

import com.dev.sphone.mod.common.items.ContainerGsm;
import com.dev.sphone.mod.common.items.GuiGsm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int GUI_GSM = 7;
    public static final int GUI_RADIO = 8; // Defina um ID para o rádio!

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUI_GSM) {
            return new ContainerGsm(player);
        }
        // Radio NÃO precisa de Container, então pode deixar assim:
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GUI_GSM) {
            return new GuiGsm(new ContainerGsm(player));
        }
        if (ID == GUI_RADIO) {
    System.out.println("Handler client: GUI_RADIO chamado!"); // Debug
    return new com.dev.sphone.mod.client.gui.phone.apps.GuiRadio().getGuiScreen();
}
        return null;
    }
}
