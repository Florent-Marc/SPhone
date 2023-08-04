package com.dev.sphone.api.voicechat;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Map;

@ForgeVoicechatPlugin
public class VoiceAddon implements VoicechatPlugin {

    public static VoicechatServerApi api;
    private static Map<String, Group> GroupMap;

    public static EntityPlayer getCallerInGroup(String callNumber,EntityPlayer except){
        for (EntityPlayer p : VoiceNetwork.getPlayers()) {
            //check if player is in group
            if (getGroup(p) == null) {
               continue;
            }
            if(getGroup(p).equals(callNumber)){
                if(!p.equals(except)){
                    return p;
                }
            }
        }
        return null;
    }

    //get other player in group qui n'est pas le receiver

    @Override
    public String getPluginId() {
        return "VoiceAddon";
    }

    @Override
    public void initialize(VoicechatApi api) {
        System.out.println("SPhone VoiceChat Addon Initialized");
    }

    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(VoicechatServerStartedEvent.class, this::onServerStarted, 100);
    }

    public void onServerStarted(VoicechatServerStartedEvent e) {
        api = e.getVoicechat();
    }

    public static void createGroup(String name, Boolean persistent, Group.Type type) {
        //random password
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += (char) (Math.random() * 26 + 97);
        }

        Group g = api.groupBuilder()
                .setName(name)
                .setPersistent(persistent)
                .setType(type)
                .setPassword(password)
                .build();
        GroupMap.put(name, g);

    }

    public static void addToGroup(String name, EntityPlayer player) {
        if (GroupMap.containsKey(name)) {
            VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
            if (connection != null) {
                Group g = GroupMap.get(name);
                connection.setGroup(g);
            }
        }
    }

    public static void removeFromActualGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            connection.setGroup(null);
        }
    }

    //get group of player
    public static String getGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return null;
        } else {
            return connection.getGroup().getName();
        }
    }

    //remove group with name
    public static void removeGroup(String name) {
        if (GroupMap.containsKey(name)) {
            GroupMap.remove(name);
        }
    }

    //check if group exists
    public static boolean groupExists(String name) {
        return GroupMap.containsKey(name);
    }

    //check if player is in a group
    public static boolean isInGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return false;
        } else {
            return connection.getGroup() != null;
        }
    }

}
