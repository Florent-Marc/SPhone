package com.dev.sphone.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SimRegisterEvent extends Event {

        private String numero;
        private String sim;
        private EntityPlayer player;

        public SimRegisterEvent(EntityPlayer player,String sim, String numero) {
            this.numero = numero;

        }

        public String getNumero() {
            return numero;
        }

        public String getSim() {
            return sim;
        }

        public EntityPlayer getPlayer() {
            return player;
        }
}
