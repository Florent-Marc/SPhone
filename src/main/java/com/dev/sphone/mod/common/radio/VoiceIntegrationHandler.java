package com.dev.sphone.mod.common.radio;

import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.mod.common.packets.server.HandlerTuneRadio;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.packets.MicrophonePacket;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.SoundCategory;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * Hooks into simple-voice-chat to cancel radio group audio when muted.
 */
public class VoiceIntegrationHandler {

    private static final Map<java.util.UUID, Long> LAST_PING = new HashMap<>();
    private static final long PING_COOLDOWN = 1000L;

    public static void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, VoiceIntegrationHandler::onMicPacket, 100);
    }

    private static void onMicPacket(MicrophonePacketEvent event) {
        VoicechatConnection sender = event.getSenderConnection();
        if (sender == null || VoiceAddon.api == null) {
            return;
        }
        if (!(sender.getPlayer().getPlayer() instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) sender.getPlayer().getPlayer();
        int freq = HandlerTuneRadio.getPlayerFrequency(player);
        if (freq <= 0) {
            return;
        }
        if (RadioMuteManager.isMuted(player.getUniqueID())) {
            return;
        }

        MicrophonePacket mic = event.getPacket();
        de.maxhenkel.voicechat.api.packets.StaticSoundPacket packet = mic.staticSoundPacketBuilder()
                .opusEncodedData(mic.getOpusEncodedData())
                .category("radio")
                .build();

        Set<java.util.UUID> members = HandlerTuneRadio.RADIO_GROUPS.get(freq);
        if (members == null) {
            return;
        }
        long now = System.currentTimeMillis();
        Long last = LAST_PING.get(player.getUniqueID());
        boolean sendPing = last == null || now - last > PING_COOLDOWN;
        if (sendPing) {
            LAST_PING.put(player.getUniqueID(), now);
        }
        for (java.util.UUID uuid : members) {
            if (uuid.equals(player.getUniqueID())) {
                continue;
            }
            VoicechatConnection conn = VoiceAddon.api.getConnectionOf(uuid);
            if (conn != null) {
                VoiceAddon.api.sendStaticSoundPacketTo(conn, packet);
                                if (sendPing && conn.getPlayer() != null && conn.getPlayer().getPlayer() instanceof EntityPlayerMP) {
                    EntityPlayerMP target = (EntityPlayerMP) conn.getPlayer().getPlayer();
                    target.connection.sendPacket(new SPacketCustomSound(
                            "sphone:radio_ping", SoundCategory.MASTER,
                            target.getPosition().getX(), target.getPosition().getY(), target.getPosition().getZ(), 1f, 1f));
                }
            }
        }
    }
}