package com.dev.sphone.mod.common.phone.sim;

import com.dev.sphone.mod.common.items.ItemSim;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class SIMContainer extends Container {
    public SIMInventory invBackpack;
    public int rows;

    public SIMContainer(InventoryPlayer playerInv, SIMInventory inv) {
        this.invBackpack = inv;
        this.rows = inv.getSizeInventory() / 9;
        int i = (this.rows - 4) * 18;
        int j = 0;
        int k = 0;

        this.addSlotToContainer(new SIMSlot(inv, 0, 8 + k * 18, 18 + j * 18));
        // Adding player's slots
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(playerInv, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(playerInv, j, 8 + j * 18, 161 + i));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public void writeToNBT(ItemStack stack) {
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        invBackpack.writeToNBT(stack.getTagCompound());
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();


            if (index < this.invBackpack.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, this.invBackpack.getSizeInventory(), this.inventorySlots.size(), true))
                    return null;
            } else if (!this.mergeItemStack(itemstack1, 0, this.invBackpack.getSizeInventory(), false)) {
                return null;
            }

            if (itemstack1.getCount() == 0)
                slot.putStack((ItemStack) null);
            else
                slot.onSlotChanged();
        }

        return itemstack;
    }


    /**
     * Used to save content
     */
    @Override
    public void onContainerClosed(EntityPlayer player) {
        this.writeToNBT(player.getHeldItem(EnumHand.MAIN_HAND));
        super.onContainerClosed(player);
    }
}