
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Settings implements ISerializable, ISerializablePacket {

    String background;
    boolean silence;

    public Settings() {}

    public Settings(String background, boolean silence) {
        this.background = background;
        this.silence = silence;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public boolean isSilence() {
        return silence;
    }

    public void setSilence(boolean silence) {
        this.silence = silence;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{background, silence};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        background = (String) objects[0];
        silence = (Boolean) objects[1];
    }

    public static void updateSettings(Settings settings) {
        String jsonFormat = "[{\"background\":" + settings.getBackground() + ",\"slience\":" + settings.isSilence() + "}]";
    }

}
