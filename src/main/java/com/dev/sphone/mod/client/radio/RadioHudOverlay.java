package com.dev.sphone.mod.client.radio;

import com.dev.sphone.api.voicemanager.voicechat.VoiceAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Simple HUD overlay to display the current radio mute state.
 */
@SideOnly(Side.CLIENT)
public class RadioHudOverlay {

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent.Text event) {
        if (!com.dev.sphone.SPhone.isModLoaded("voicechat") || VoiceAddon.api == null) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.player == null) return;
        String group = VoiceAddon.getGroup(mc.player);
        if (group != null && group.toLowerCase().startsWith("radio_")) {
            String key = RadioKeyHandler.isMuted() ? "sphone.radio.muted" : "sphone.radio.active";
            mc.fontRenderer.drawStringWithShadow(I18n.format(key), 5, 5, 0xFFFFFF);
        }
    }
}