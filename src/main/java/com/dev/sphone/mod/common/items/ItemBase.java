package com.dev.sphone.mod.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase(String name, CreativeTabs creativeTabs, int stackcount, int durability) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        this.setMaxDamage(durability);
        //ItemsRegister.INSTANCE.getItems().add(this);
    }
}
