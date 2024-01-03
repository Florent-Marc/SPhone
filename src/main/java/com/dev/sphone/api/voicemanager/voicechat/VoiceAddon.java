package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import com.dev.sphone.mod.common.packets.client.PacketPlayerHudState;
import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.net.NetManager;
import de.maxhenkel.voicechat.net.RemoveGroupPacket;
import de.maxhenkel.voicechat.voice.common.PlayerState;
import de.maxhenkel.voicechat.voice.server.PlayerStateManager;
import de.maxhenkel.voicechat.voice.server.Server;
import de.maxhenkel.voicechat.voice.server.ServerGroupManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ForgeVoicechatPlugin
public class VoiceAddon implements VoicechatPlugin {

    public static VoicechatServerApi api;
    private static final Map<String, Group> GroupMap = new HashMap<>();

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
            }
        }
    }

    public static void removeFromActualGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            String getGroup = getGroup(player);
            removeGroup(getGroup);


            Server server = Voicechat.SERVER.getServer();
            if(server != null) {
                ServerGroupManager groupManager = server.getGroupManager();
                if(connection.getGroup() != null) {
                    UUID groupId = connection.getGroup().getId();

                    PlayerStateManager manager = server.getPlayerStateManager();
                    for(PlayerState state : manager.getStates()){
                        if(state.hasGroup() && state.getGroup().equals(groupId)){
                            VoicechatConnection target = api.getConnectionOf(state.getUuid());
                            if (target != null && target.getGroup() != null) {
                                api.removeGroup(target.getGroup().getId());
                                target.setGroup(null);
                                if (target.getPlayer().getPlayer() instanceof EntityPlayer) {
                                    EntityPlayer targetPlayer = (EntityPlayer) target.getPlayer().getPlayer();
                                    SPhone.network.sendTo(new PacketOpenPhone("home", ""), (EntityPlayerMP) targetPlayer);
                                }
                            }

                        }
                    }


                    groupManager.removeGroup(groupId);

                    broadcastRemoveGroup(server, groupId);
                }
            }

            if(connection.getGroup() != null) {
                api.removeGroup(connection.getGroup().getId());
                connection.setGroup(null);
            }
            //SPhone.network.sendTo(new PacketPlayerHudState(true), (EntityPlayerMP) player);
        }
    }

    private static void broadcastRemoveGroup(Server server, UUID group) {
        RemoveGroupPacket packet = new RemoveGroupPacket(group);
        server.getServer().getPlayerList().getPlayers().forEach(p -> NetManager.sendToClient(p, packet));
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
        GroupMap.remove(name);
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
