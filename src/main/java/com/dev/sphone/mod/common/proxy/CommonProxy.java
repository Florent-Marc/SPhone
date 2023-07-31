/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.common.proxy;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.capa.StatsProvider;
import com.dev.sphone.mod.common.capa.StatsStorage;
import com.dev.sphone.mod.common.capa.maison;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {

    public void preInit() {
    }

    public void init() {

    }
    @SubscribeEvent
    public static void registercapa(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof EntityPlayer) {
            event.addCapability(new ResourceLocation(SPhone.MOD_ID, "stats"), new StatsProvider(new StatsStorage(0,new maison("d",0))));
        }
    }


}
