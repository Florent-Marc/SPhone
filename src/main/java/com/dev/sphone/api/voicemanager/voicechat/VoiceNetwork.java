package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.api.voicemanager.IVoiceManager;
import de.maxhenkel.voicechat.api.Group;
import net.minecraft.entity.player.EntityPlayerMP;

public class VoiceNetwork implements IVoiceManager {

    public VoiceNetwork() {
    }

    @Override
    public void addPlayertoCall(EntityPlayerMP player, String callNumber) {
        System.out.println("addPlayertoCall : " + callNumber);
        if(VoiceAddon.groupExists(callNumber)) {
            System.out.println("addPlayertoCall : " + callNumber + " exists");
            VoiceAddon.addToGroup(callNumber, player);
        } else {
            System.out.println("addPlayertoCall : " + callNumber + " doesn't exists");
            VoiceAddon.createGroup(callNumber, false, Group.Type.ISOLATED);
            VoiceAddon.addToGroup(callNumber, player);
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
