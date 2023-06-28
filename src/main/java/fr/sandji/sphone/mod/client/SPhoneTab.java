/*
 * SITE-23 V2 - Tout droits réservés !
 */

package fr.sandji.sphone.mod.client;

import fr.sandji.sphone.mod.common.register.ItemsRegister;
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
