package com.dev.sphone.mod.common.capa;

import net.minecraft.nbt.NBTTagCompound;

public class maison {
    private String adresse;
    private int prix;

    public maison(String adresse, int prix) {
        this.adresse = adresse;
        this.prix = prix;
    }

    public String getAdresse() {
        return this.adresse;
    }


    public int getPrix() {
        return this.prix;
    }


    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }


    public void setPrix(int prix) {
        this.prix = prix;
    }

    //get nbt
    public NBTTagCompound getNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("adresse", this.adresse);
        nbt.setInteger("prix", this.prix);
        return nbt;
    }

    //set nbt
    public void setNBT(NBTTagCompound nbt) {
        this.adresse = nbt.getString("adresse");
        this.prix = nbt.getInteger("prix");
    }
}
