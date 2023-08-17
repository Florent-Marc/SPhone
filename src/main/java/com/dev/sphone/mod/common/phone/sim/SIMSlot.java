package com.dev.sphone.mod.common.phone.sim;

import com.dev.sphone.mod.common.items.ItemSim;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SIMSlot extends Slot {
    public SIMSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }


    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemSim;
    }
}
