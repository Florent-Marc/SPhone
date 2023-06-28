
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.items;

import fr.sandji.sphone.mod.common.register.ItemsRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemPhone extends Item {

    public ItemPhone(String name, CreativeTabs creativeTabs, int stackcount) {
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(creativeTabs);
        this.setMaxStackSize(stackcount);
        ItemsRegister.INSTANCE.getItems().add(this);
    }



}
