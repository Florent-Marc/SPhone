package com.dev.sphone.api.voicechat;

import com.dev.sphone.mod.common.items.ItemPhone;
import com.dev.sphone.mod.server.bdd.MethodesBDDImpl;
import com.dev.sphone.mod.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.HashMap;

public class VoiceNetwork {

    private static HashMap<EntityPlayer, String> network = new HashMap<>();

    public static boolean isPlayerInNetwork(EntityPlayer p) {
        return network.containsKey(p);
    }

    public static void addPlayerToNetwork(EntityPlayer p) {
        if (hasPhone(p)) {
            network.put(p, MethodesBDDImpl.getNumero(Utils.getSimCard(p)));
            System.out.println("Player " + p.getName() + " is now in the network with numero " + MethodesBDDImpl.getNumero(Utils.getSimCard(p)) );
        } else {
            System.out.println("Player " + p.getName() + " has no phone");
        }
    }
    public static boolean hasPhone(EntityPlayer player) {
        return ItemPhone.getSimCard(player.getHeldItemMainhand()) != 0;
    }

    public static void removePlayerFromNetwork(EntityPlayer p) {
        network.remove(p);
        System.out.println("Player " + p.getName() + " is now out of the network");
    }

    public static boolean hasNumberInNetwork(String number) {
        return network.containsValue(number);
    }

    public static EntityPlayer getPlayerFromNumber(String number) {
        for (EntityPlayer p : network.keySet()) {
            if (network.get(p).equals(number)) {
                return p;
            }
        }
        return null;
    }

    public static EntityPlayer[] getPlayers() {
        return network.keySet().toArray(new EntityPlayer[network.keySet().size()]);
    }

    @SubscribeEvent
    public void onPlayerJoinNetwork(PlayerEvent.PlayerLoggedInEvent e) {
        addPlayerToNetwork(e.player);
    }

    @SubscribeEvent
    public void onPlayerLeaveNetwork(PlayerEvent.PlayerLoggedOutEvent e) {
        removePlayerFromNetwork(e.player);
    }

    /**
     * Update the network of a player <br>
     * Check if the player is in the network<br>
     * If yes, replace the player's network with the new numero <br>
     * If no, add the player to the network
     */
    public static void updateNetwork(EntityPlayer p) {
        if (isPlayerInNetwork(p)) {
            network.replace(p, MethodesBDDImpl.getNumero(Utils.getSimCard(p)));
        } else {
            addPlayerToNetwork(p);
        }
    }



    //TODO packet request data verif network with player


}
