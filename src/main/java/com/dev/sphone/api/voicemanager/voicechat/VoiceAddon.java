package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketPlayerHudState;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashMap;
import java.util.Map;

@ForgeVoicechatPlugin
public class VoiceAddon implements VoicechatPlugin {

    public static VoicechatServerApi api;
    private static Map<String, Group> GroupMap = new HashMap<>();

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
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append((char) (Math.random() * 26 + 97));
        }

        Group g = api.groupBuilder()
                .setName(name)
                .setPersistent(persistent)
                .setType(type)
                .setPassword(password.toString())
                .build();
        GroupMap.put(name, g);

    }

    public static void addToGroup(String name, EntityPlayer player) {
        if (GroupMap.containsKey(name)) {
            VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
            if (connection != null) {
                Group g = GroupMap.get(name);
                connection.setGroup(g);
                SPhone.network.sendTo(new PacketPlayerHudState(false), (EntityPlayerMP) player);
                System.out.println("Player " + player.getName() + " is now in group " + name);
            }
        }
    }

    public static void removeFromActualGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            System.out.println("Player " + player.getName() + " is now out of group " + getGroup(player));
            connection.setGroup(null);
            //SPhone.network.sendTo(new PacketPlayerHudState(true), (EntityPlayerMP) player);
        }
    }

    public static String getGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return null;
        } else {
            if (connection.getGroup() == null) {
                return null;
            }
            return connection.getGroup().getName();
        }
    }

    public static void removeGroup(String name) {
        if (GroupMap.containsKey(name)) {
            GroupMap.remove(name);
        }
    }

    public static boolean groupExists(String name) {
        return GroupMap.containsKey(name);
    }

    public static boolean isInGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return false;
        } else {
            return connection.getGroup() != null;
        }
    }

}
