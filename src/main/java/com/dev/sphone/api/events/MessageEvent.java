package com.dev.sphone.api.events;

import com.dev.sphone.mod.common.phone.Message;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;


public class MessageEvent extends Event {

    public MessageEvent() {
    }

    @Cancelable
    public static class Send extends MessageEvent {
        private String numero;
        private Message message;

        public Send(String numero, Message message1) {
            this.numero = numero;
            this.message = message1;
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
    }
}
