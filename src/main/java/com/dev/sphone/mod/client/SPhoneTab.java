/*
 * SITE-23 V2 - Tout droits réservés !
 */

package com.dev.sphone.mod.client;

import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SPhoneTab extends CreativeTabs {

    public SPhoneTab(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemsRegister.ITEM_PHONE);
    }
}
