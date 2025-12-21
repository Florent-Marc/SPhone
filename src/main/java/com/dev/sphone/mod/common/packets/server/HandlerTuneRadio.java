package com.dev.sphone.mod.common.packets.server;

import com.dev.sphone.mod.common.packets.client.PacketTuneRadio;
import com.dev.sphone.api.voicemanager.VoiceManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import java.util.*;

public class HandlerTuneRadio implements IMessageHandler<PacketTuneRadio, IMessage> {

    public static final Map<Integer, Set<UUID>> RADIO_GROUPS = new HashMap<>();

    @Override
    public IMessage onMessage(PacketTuneRadio message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        int freq = message.getFrequency();

        player.getServerWorld().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                removeFromAllGroups(player);
                if (VoiceManager.voiceManager != null) {
                    VoiceManager.voiceManager.removePlayerFromCall(player);
                }

            if (freq == 0) {
                player.sendMessage(new net.minecraft.util.text.TextComponentString(
                        "Você saiu da frequência"
                ));
            } else if (freq >= 1 && freq <= 1000) {
                RADIO_GROUPS.computeIfAbsent(freq, k -> new HashSet<>()).add(player.getUniqueID());
                player.sendMessage(new net.minecraft.util.text.TextComponentString(
                    "Você entrou na frequência: " + freq
                ));
            } else {
                player.sendMessage(new net.minecraft.util.text.TextComponentString(
                    "Frequência inválida. Use de 1 a 1000."
                ));
            }
        }
        });
        return null;
    }

    public static void removeFromAllGroups(EntityPlayerMP player) {
        for (Set<UUID> group : RADIO_GROUPS.values()) {
            group.remove(player.getUniqueID());
        }
    }

    public static int getPlayerFrequency(EntityPlayerMP player) {
        for (Map.Entry<Integer, Set<UUID>> entry : RADIO_GROUPS.entrySet()) {
            if (entry.getValue().contains(player.getUniqueID())) {
                return entry.getKey();
            }
        }
        return -1;
    }
}