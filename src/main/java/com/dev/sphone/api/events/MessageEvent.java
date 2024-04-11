package com.dev.sphone.api.events;

import com.dev.sphone.mod.common.phone.Message;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;


public class MessageEvent extends Event {

    public MessageEvent() {
    }

    @Cancelable
    public static class Send extends MessageEvent {
        private String numero;
        private Message message;
        private EntityPlayerMP receiver;
        private EntityPlayer sender;
        private String receiverTarget;
        private String senderTarget;


        public Send(String numero, Message message1) {
            this.numero = numero;
            this.message = message1;
        }

        public Send(String sender, EntityPlayer player, String numero, EntityPlayerMP receiverTarget, Message message1) {
            this.numero = numero;
            this.message = message1;
            this.receiver = receiverTarget;
            this.sender = player;
            this.receiverTarget = numero;
            this.senderTarget = sender;
        }

        /**
            return numero of the sender
         **/
        public String getSender() {
            return numero;
        }

        /**
            return the message
         **/
        public Message getMessage() {
            return message;
        }


        public String getNumero() {
            return numero;
        }

        public EntityPlayerMP getReceiver() {
            return receiver;
        }

        public String getReceiverTarget() {
            return receiverTarget;
        }

        public String getSenderTarget() {
            return senderTarget;
        }

        public EntityPlayer getSenderPlayer() {
            return sender;
        }
    }

    @Cancelable
    public static class Load extends MessageEvent {
        private EntityPlayerMP player;
        private int sim;

        public Load(EntityPlayerMP player, int sim) {
            this.player = player;
            this.sim = sim;
        }

        public EntityPlayerMP getPlayer() {
            return player;
        }

        public int getSim() {
            return sim;
        }
    }

}
