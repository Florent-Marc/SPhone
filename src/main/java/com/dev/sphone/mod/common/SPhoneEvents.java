package com.dev.sphone.mod.common;

import com.dev.sphone.mod.common.register.ItemsRegister;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import com.dev.sphone.mod.common.packets.server.HandlerTuneRadio;

public class SPhoneEvents {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player.world.isRemote) return; // Só servidor
        if (!com.dev.sphone.SPhone.isModLoaded("voicechat")) {
            return;
        }
        EntityPlayer player = event.player;

        // Verifica se o jogador está em uma frequência de rádio
        int freq = HandlerTuneRadio.getPlayerFrequency((EntityPlayerMP) player);

        if (freq != -1) {
            // Para rádio: checa se possui o item nas hotbars
            boolean hasRadioInHotbar = false;
            for (int i = 0; i < 9; i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);
                if (stack != null && !stack.isEmpty() && stack.getItem() == ItemsRegister.RADIO) {
                    hasRadioInHotbar = true;
                    break;
                }
            }
            if (!hasRadioInHotbar) {
                HandlerTuneRadio.removeFromAllGroups((EntityPlayerMP) player);
            }
        } else {
            // Para outros grupos: só pode permanecer se o celular estiver na mão principal ou offhand
            String groupName = VoiceAddon.getGroup(player);
            if (groupName != null && !groupName.isEmpty()) {
                // Para outros grupos: só pode permanecer se o celular estiver na mão principal ou offhand
                boolean holdingPhone =
                        (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() == ItemsRegister.ITEM_PHONE) ||
                        (!player.getHeldItemOffhand().isEmpty() && player.getHeldItemOffhand().getItem() == ItemsRegister.ITEM_PHONE);
                if (!holdingPhone) {
                    VoiceAddon.removeFromActualGroup(player);
                }
            }
        }
    }
}
