package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.api.voicemanager.IVoiceManager;
import com.dev.sphone.api.voicemanager.VoiceManager;
import de.maxhenkel.voicechat.api.Group;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Collections;
import java.util.Objects;

public class VoiceNetwork implements IVoiceManager {

    public VoiceNetwork() {
    }

    @Override
    public void addPlayertoCall(EntityPlayerMP player, String callNumber) {
        if(VoiceAddon.groupExists(callNumber)) {
            System.out.println("addPlayertoCall : " + callNumber + " exists");
            VoiceAddon.addToGroup(callNumber, player);
        } else {
            System.out.println("addPlayertoCall : " + callNumber + " doesn't exists");
            VoiceAddon.createGroup(callNumber, false, Group.Type.ISOLATED);
            VoiceAddon.addToGroup(callNumber, player);
        }
        if(VoiceManager.callMap.containsKey(callNumber)) {
            VoiceManager.callMap.get(callNumber).add(player);
        } else {
            VoiceManager.callMap.put(callNumber, Collections.singletonList(player));
        }
    }

    @Override
    public void removePlayerFromCall(EntityPlayerMP player) {
        VoiceAddon.removeFromActualGroup(player);
    }

    @Override
    public boolean isPlayerInCall(EntityPlayerMP player) {
        return VoiceAddon.isInGroup(player);
    }

    @Override
    public void initAddon() {

    }


    //TODO packet request data verif network with player


}
