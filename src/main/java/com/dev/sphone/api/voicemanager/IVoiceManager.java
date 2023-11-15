package com.dev.sphone.api.voicemanager;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IVoiceManager {
    void addPlayertoCall(EntityPlayerMP player, String callNumber);
    void removePlayerFromCall(EntityPlayerMP player);
    boolean isPlayerInCall(EntityPlayerMP player);

    /**
     *
     * @apiNote À utiliser si besoin.
     *
     * */
    void initAddon();
}
