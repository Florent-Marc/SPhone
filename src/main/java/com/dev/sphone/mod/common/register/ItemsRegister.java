
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.common.register;

import com.dev.sphone.mod.common.items.ItemPhone;
import com.google.common.collect.Lists;
import com.dev.sphone.SPhone;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ItemsRegister {

    public static final ItemsRegister INSTANCE = new ItemsRegister();
    private List<Item> items;

    public static ItemPhone ITEM_PHONE;

    public void register() {
        ITEM_PHONE = new ItemPhone("phone", SPhone.SPHONE_TAB, 1);
    }

    public void init() {
        items = Lists.newArrayList();
        register();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }

    //==================================================FORGE===========================================================

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
        for (Item item : items) {
            registerItemsModels(item);
        }
    }

    public static void registerItemsModels(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(new ResourceLocation(SPhone.MOD_ID, item.getTranslationKey().substring(5)), "inventory"));
    }
}
