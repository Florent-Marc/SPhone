package com.dev.sphone.mod.common.capa;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;


public class StatsStorage implements IStats, INBTSerializable<NBTTagCompound> {

    private int money;
    private maison maison;

    public StatsStorage(int money, maison maison) {
        this.money = money;

    }

    @Override
    public int getMoney() {
        return this.money;
    }

    @Override
    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public void addMoney(int money) {
        this.money += money;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("money", this.money);
        nbt.setTag("maison", this.maison.getNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.money = nbt.getInteger("money");
        this.maison.setNBT(nbt.getCompoundTag("maison"));
    }
}
