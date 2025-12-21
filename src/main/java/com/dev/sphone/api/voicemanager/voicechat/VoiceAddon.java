package com.dev.sphone.api.voicemanager.voicechat;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.packets.client.PacketOpenPhone;
import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.net.NetManager;
import de.maxhenkel.voicechat.net.RemoveGroupPacket;
import de.maxhenkel.voicechat.api.events.SoundPacketEvent;
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
import java.util.ArrayList;
import java.util.List;

@ForgeVoicechatPlugin
public class VoiceAddon implements VoicechatPlugin {

    public static VoicechatServerApi api;
    private static final Map<String, Group> GroupMap = new HashMap<>();
    /** Tracks players who muted their microphone for group calls. */
    private static final Map<UUID, Boolean> MutedPlayers = new HashMap<>();

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
        // Existing handler for call group muting
        registration.registerEvent(SoundPacketEvent.class, VoiceAddon::onSoundPacket, 100);
        // Handler for radio specific muting
        com.dev.sphone.mod.common.radio.VoiceIntegrationHandler.registerEvents(registration);
        registration.registerEvent(de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent.StaticSound.class,
                new com.dev.sphone.mod.client.radio.RadioAudioFilter()::onClientSound, 100);
    }

    public void onServerStarted(VoicechatServerStartedEvent e) {
        api = e.getVoicechat();
        
        if (api == null) {
            System.out.println("VoiceAddon.api is NULL - Cannot create group!");
            return;
        }
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
            }
        }
    }

    /**
     * Removes the player from their current voice chat group without deleting the group.
     * This is used to allow temporary call muting while keeping the group intact.
     */
    public static void leaveGroup(EntityPlayer player) {
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null && connection.getGroup() != null) {
            connection.setGroup(null);
        }
    }

    /** Sets the muted state for the given player in group calls. */
    public static void setGroupMute(EntityPlayerMP player, boolean mute) {
        MutedPlayers.put(player.getUniqueID(), mute);
    }

    /** Returns true if the player muted their microphone for group calls. */
    public static boolean isGroupMuted(EntityPlayerMP player) {
        return MutedPlayers.getOrDefault(player.getUniqueID(), false);
    }

    public static void removeFromActualGroup(EntityPlayer player) {
                if (api == null) {
            return;
        }
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection != null) {
            String groupName = getGroup(player);
            if (groupName != null) {
                // Remove apenas o próprio player do grupo
                connection.setGroup(null);

                // Agora verifica se ficou vazio!
                List<EntityPlayer> players = getPlayersInGroup(groupName);
                if (players.isEmpty()) {
                    // NINGUÉM no grupo, pode deletar
                    Group group = GroupMap.get(groupName);

                    removeGroup(groupName);

                    if (group != null) {
                        Server server = Voicechat.SERVER.getServer();
                        if (server != null) {
                            ServerGroupManager groupManager = server.getGroupManager();
                            groupManager.removeGroup(group.getId());
                            broadcastRemoveGroup(server, group.getId());
                        }
                        api.removeGroup(group.getId());
                    }
                }
            }
        }
    }

    // NOVO: Conta quantos players ainda estão no grupo
    public static List<EntityPlayer> getPlayersInGroup(String groupName) {
        List<EntityPlayer> players = new ArrayList<>();
        if (api == null) return players;

        Group group = GroupMap.get(groupName);
        if (group == null) return players;

        Server server = Voicechat.SERVER.getServer();
        if (server == null) return players;
        PlayerStateManager manager = server.getPlayerStateManager();

        for (PlayerState state : manager.getStates()) {
            if (state.hasGroup() && state.getGroup().equals(group.getId())) {
                VoicechatConnection conn = api.getConnectionOf(state.getUuid());
                if (conn != null && conn.getPlayer() != null && conn.getPlayer().getPlayer() instanceof EntityPlayer) {
                    players.add((EntityPlayer) conn.getPlayer().getPlayer());
                }
            }
        }
        return players;
    }

    private static void broadcastRemoveGroup(Server server, UUID group) {
        RemoveGroupPacket packet = new RemoveGroupPacket(group);
        server.getServer().getPlayerList().getPlayers().forEach(p -> NetManager.sendToClient(p, packet));
    }

    public static String getGroup(EntityPlayer player) {
                if (api == null) {
            return null;
        }
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
                if (api == null) {
            return false;
        }
        VoicechatConnection connection = api.getConnectionOf(player.getUniqueID());
        if (connection == null) {
            return false;
        }
        return connection.getGroup() != null;
    }
    
        /**
     * Cancels sound packets destined for group chat when the sender muted their
     * group microphone. This still allows proximity and whisper voice to work
     * normally so players can talk with nearby players while muted.
     */
    private static void onSoundPacket(SoundPacketEvent<?> event) {
        VoicechatConnection sender = event.getSenderConnection();
        if (sender == null) {
            return;
        }
        if (!(sender.getPlayer().getPlayer() instanceof EntityPlayerMP)) {
            return;
        }
                if (!SoundPacketEvent.SOURCE_GROUP.equals(event.getSource())) {
            return;
        }
        EntityPlayerMP player = (EntityPlayerMP) sender.getPlayer().getPlayer();
        if (isGroupMuted(player)) {
            event.cancel();
        }
    }
}
