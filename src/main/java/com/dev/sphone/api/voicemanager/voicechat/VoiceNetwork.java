package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.api.voicemanager.IVoiceManager;
import net.minecraft.entity.player.EntityPlayerMP;
import de.maxhenkel.voicechat.api.Group;

public class VoiceNetwork implements IVoiceManager {

    public VoiceNetwork() {
    }

    @Override
public void addPlayertoCall(EntityPlayerMP player, String callNumber) {
    if (VoiceAddon.groupExists(callNumber)) {
        System.out.println("addPlayertoCall : " + callNumber + " exists");
        VoiceAddon.addToGroup(callNumber, player);
    } else {
        System.out.println("addPlayertoCall : " + callNumber + " doesn't exists");
        Group.Type type = Group.Type.OPEN;
        if (callNumber.toLowerCase().startsWith("radio_")) {
            type = Group.Type.NORMAL;
        }
        VoiceAddon.createGroup(callNumber, false, type);
        VoiceAddon.addToGroup(callNumber, player);
    }
}

    @Override
    public void removePlayerFromCall(EntityPlayerMP player) {
        // Pega o grupo atual do player
        String group = VoiceAddon.getGroup(player);
        if (group != null) {
            VoiceAddon.removeFromActualGroup(player);

            // Depois de remover, verifica se está vazio e deleta só se NÃO tiver ninguém
            if (VoiceAddon.getPlayersInGroup(group).isEmpty()) {
                VoiceAddon.removeGroup(group);
                System.out.println("Grupo " + group + " deletado pois ficou vazio!");
            }
        }
    }

    @Override
    public boolean isPlayerInCall(EntityPlayerMP player) {
        return VoiceAddon.isInGroup(player);
    }

    @Override
    public void initAddon() {
        // Implementação opcional
    }
}