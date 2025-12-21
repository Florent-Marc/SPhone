package com.dev.sphone.mod.common.register;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.common.items.ItemSim;
import com.dev.sphone.mod.common.items.ItemRadio; // <-- Import do novo item!
import com.google.common.collect.Lists;
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
    public static ItemSim SIM_CARD;
    public static ItemRadio RADIO; // <-- Novo campo

public void register() {
    ITEM_PHONE = new ItemPhone("phone", SPhone.SPHONE_TAB, 1);
    addItem(ITEM_PHONE);

    SIM_CARD = new ItemSim("sim_card", SPhone.SPHONE_TAB, 1);
    addItem(SIM_CARD);

    RADIO = new ItemRadio("radio", SPhone.SPHONE_TAB, 1);
    addItem(RADIO);
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
        ModelLoader.setCustomModelResourceLocation(
            item, 0,
            new ModelResourceLocation(
                new ResourceLocation(SPhone.MOD_ID, item.getTranslationKey().substring(5)), "inventory"
            )
        );
    }
}
