package com.dev.sphone.mod.common;

import com.dev.sphone.mod.common.items.ContainerGsm;
import com.dev.sphone.mod.common.items.GuiGsm;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 7) {
            return new ContainerGsm(player);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 7) {
            return new GuiGsm(new ContainerGsm(player));
        }
        return null;
    }
}
