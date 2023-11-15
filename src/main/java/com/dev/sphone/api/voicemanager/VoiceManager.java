package com.dev.sphone.api.voicemanager;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.voicemanager.voicechat.VoiceNetwork;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;

public class VoiceManager {

    public static HashMap<String, String> requestCallMap = new HashMap<>();

    public static IVoiceManager voiceManager = null;

    public static void init(Side side) {

        if(SPhone.isUsingMod("de.maxhenkel.voicechat.ForgeVoicechatMod"))
            voiceManager = new VoiceNetwork();
        else {
            SPhone.logger.error("VoiceManager : VoiceChat mod is missing, please install it.");

        }
        voiceManager.initAddon();
    }
}
