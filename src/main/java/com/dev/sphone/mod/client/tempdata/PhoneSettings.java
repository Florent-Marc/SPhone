package com.dev.sphone.mod.client.tempdata;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class PhoneSettings implements INBTSerializable {

    private String background = "acsgui";

    public PhoneSettings(String background) {
        this.background = background;
    }

    @Override
    public NBTBase serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("background", background);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        if(nbt instanceof NBTTagCompound) {
            NBTTagCompound tag = (NBTTagCompound) nbt;
            background = tag.getString("background");
        }
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
