package com.dev.sphone.mod.common.phone.sim;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.items.ItemSim;
import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import java.util.Arrays;
import java.util.Collections;

public class SIMInventory implements IInventory
{
    public ItemStack[] content;
    public int size;

    public SIMInventory(ItemStack container, int size) {
        this.size = size;
        ItemStack sim = new ItemStack(ItemsRegister.SIM_CARD);
        NBTTagCompound comp = new NBTTagCompound();
        System.out.println(container);
        if(container.getTagCompound().getInteger(ItemSim.SIM_KEY_TAG) == 0) {
            this.content = Collections.singletonList(new ItemStack(Items.AIR)).toArray(new ItemStack[size]);
        } else {
            comp.setInteger(ItemSim.SIM_KEY_TAG, container.getTagCompound().getInteger(ItemSim.SIM_KEY_TAG));
            sim.setTagCompound(comp);
            this.content = Collections.singletonList(sim).toArray(new ItemStack[size]);
        }

        if (!container.hasTagCompound()) container.setTagCompound(new NBTTagCompound());
        assert container.getTagCompound() != null;
        this.readFromNBT(container.getTagCompound());
    }

    /**
     * This methods reads the content of the NBTTagCompound inside the container
     *
     * @param comp
     *            the container NBTTagCompound
     */
    public void readFromNBT(NBTTagCompound comp) {
        NBTTagList nbtlist = comp.getTagList("Inventory", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbtlist.tagCount(); i++) {
            NBTTagCompound comp1 = nbtlist.getCompoundTagAt(i);
            int slot = comp1.getInteger("Slot");
            this.content[slot] = new ItemStack(comp1);
        }
    }

    /**
     * This methods saves the content inside the container
     *
     * @param comp
     *            the NBTTagCompound to write in
     */
    public void writeToNBT(NBTTagCompound comp) {
        NBTTagList nbtlist = new NBTTagList();

        for (int i = 0; i < this.size; i++) {
            if (this.content[i] != null) {
                NBTTagCompound comp1 = new NBTTagCompound();
                comp1.setInteger("Slot", i);
                this.content[i].writeToNBT(comp1);
                nbtlist.appendTag(comp1);
            }
        }
        comp.setTag("Inventory", nbtlist);
    }

    @Override
    public int getSizeInventory() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.content[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack stack = getStackInSlot(index);
        if (stack != null) {
            if (stack.getCount() > amount) {
                stack = stack.splitStack(amount);
                if (stack.getCount() == 0) this.content[index] = null;
            } else {
                this.content[index] = null;
            }
        }
        return stack;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }


    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.content[index] = stack;
    }


    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }


    /**
     * Prevents backpack-ception
     */
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return (stack.getItem() instanceof ItemSim);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }
}