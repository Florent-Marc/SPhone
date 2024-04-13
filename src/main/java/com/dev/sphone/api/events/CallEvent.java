package com.dev.sphone.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CallEvent extends Event {

    private EntityPlayer player;
    private String callnumber;

    public CallEvent(EntityPlayer player, String callnumber) {
        this.player = player;
        this.callnumber = callnumber;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public String getCallNumber() {
        return callnumber;
    }


    public static class JoinCall extends CallEvent {
        public JoinCall(EntityPlayer player,String targetnumber) {
            super(player, targetnumber);

        }
    }

    public static class LeaveCall extends CallEvent {
        public LeaveCall(EntityPlayer player, String callnumber) {
            super(player, callnumber);
        }
    }

    @Cancelable
    public static class CreateCall extends CallEvent {
        public CreateCall(EntityPlayer player, String number) {
            super(player, number);
        }
    }
}
