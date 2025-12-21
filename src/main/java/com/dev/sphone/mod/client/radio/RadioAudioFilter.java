package com.dev.sphone.mod.client.radio;

import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import uk.me.berndporr.iirj.*;

/**
 * Applies a simple radio filter to incoming voice chat when tuned to a radio group.
 */
public class RadioAudioFilter {

    private final Butterworth filter;

    public RadioAudioFilter() {
        filter = new Butterworth();
        // Apply a high pass filter to remove low frequencies and mimic the
        // typical walkie talkie sound. Cut frequencies below ~1000 Hz.
        // Ripple factor set to 1 dB to keep the effect subtle.
        filter.bandPass(2, 48000, 2000, 1200, 1);
    }

    public void onClientSound(ClientReceiveSoundEvent.StaticSound event) {
        short[] samples = event.getRawAudio();
        for (int i = 0; i < samples.length; i++) {
            double normalized = samples[i] / 32768.0;
            double filtered = filter.filter(normalized) * 8.0;
            filtered = Math.max(-1.0, Math.min(1.0, filtered));
            samples[i] = (short) (filtered * 32767.0);
        }
        event.setRawAudio(samples);
    }
}