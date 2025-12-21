package com.dev.sphone.mod.common.radio;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps track of players who muted their radio microphone.
 */
public class RadioMuteManager {

    private static final Map<UUID, Boolean> MUTED = new ConcurrentHashMap<>();

    /** Sets the mute state for a player. */
    public static void setMuted(UUID player, boolean muted) {
        MUTED.put(player, muted);
    }

    /** Returns true if the given player has their radio muted. */
    public static boolean isMuted(UUID player) {
        return MUTED.getOrDefault(player, false);
    }
}