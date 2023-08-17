package com.dev.sphone.mod.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGsm extends Container {

    private ItemStackHandler handler;

    public ContainerGsm(EntityPlayer player) {
        super();

        ItemStack hold = (player.getHeldItemMainhand());
        if (hold.hasTagCompound()) {
            if (hold.getTagCompound().hasKey("inventory")) {
                handler = new ItemStackHandler(1);
                handler.deserializeNBT(hold.getTagCompound().getCompoundTag("inventory"));
            } else {
                handler = new ItemStackHandler(1);
                hold.getTagCompound().setTag("inventory", handler.serializeNBT());
            }
        } else {
            handler = new ItemStackHandler(1);
            hold.setTagCompound(hold.getTagCompound());
        }

        this.addSlotToContainer(new SlotCard(handler, 0, 80, 36));

        InventoryPlayer playerInv = player.inventory;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInv, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        ItemStack hold = player.getHeldItemMainhand();
        NBTTagCompound tag = hold.getTagCompound();
        if (tag == null) tag = new NBTTagCompound();
        NBTTagCompound inventory = new NBTTagCompound();
        inventory = handler.serializeNBT();
        tag.setTag("inventory", inventory);
        hold.setTagCompound(tag);

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        //si l'item est un ItemSim transferer dans le slot 0
        //sinon transferer dans l'inventaire du joueur
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();

            itemstack = itemstack1.copy();
            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    private class SlotCard extends SlotItemHandler {
        public SlotCard(ItemStackHandler handler, int i, int i1, int i2) {
            super(handler, i, i1, i2);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ItemSim;
        }
    }
}
