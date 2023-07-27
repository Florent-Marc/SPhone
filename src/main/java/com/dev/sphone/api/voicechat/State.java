package com.dev.sphone.api.voicechat;

import net.minecraft.entity.player.EntityPlayerMP;

public class State {

    private EntityPlayerMP player;
    private String state;
    private String group;

    public State(EntityPlayerMP player, String state, String group) {
        this.player = player;
        this.state = state;
        this.group = group;
    }

    public EntityPlayerMP getPlayer() {
        return player;
    }

    public String getState() {
        return state;
    }


    public String getGroup() {
        return group;
    }

    public void setState(String state) {
        this.state = state;
    }
}
