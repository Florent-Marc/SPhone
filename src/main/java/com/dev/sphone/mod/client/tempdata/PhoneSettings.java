package com.dev.sphone.mod.client.tempdata;

import com.dev.sphone.mod.client.gui.phone.AppManager;
import com.dev.sphone.mod.common.items.ItemPhone;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class PhoneSettings implements INBTSerializable {

    private String background = "acsgui";

    public PhoneSettings(String background) {
        this.background = background;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("background", this.background);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        if (nbt instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            this.background = tag.getString("background");
        }
    }

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    //save settings
    public static void saveSettings(ItemStack phone, PhoneSettings settings) {
        if (phone.getItem() instanceof ItemPhone) {
            ItemPhone item = (ItemPhone) phone.getItem();
            NBTTagCompound nbt = phone.getTagCompound();
            if (nbt == null) {
                nbt = new NBTTagCompound();
            }
            nbt.setTag("settings", settings.serializeNBT());
            phone.setTagCompound(nbt);
        }
    }

    @Override
    public String toString() {
        return "PhoneSettings{" +
                "background='" + background + '\'' +
                '}';
    }
}
