package com.dev.sphone.mod.common.phone;

import com.dev.sphone.mod.client.gui.phone.AppManager;
import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

import java.util.ArrayList;
import java.util.List;

public class Settings implements ISerializable, ISerializablePacket {

    String background;
    boolean silence;
    List<AppManager.App> downlaodedApps = new ArrayList<>();

    public Settings() {}

    public Settings(String background, boolean silence) {
        this.background = background;
        this.silence = silence;
        this.downlaodedApps = new ArrayList<>();
    }

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }
    public boolean isSilence() { return silence; }
    public void setSilence(boolean silence) { this.silence = silence; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{background, silence, downlaodedApps};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        background = (String) data.next();
        silence = (boolean) data.next();
        downlaodedApps = (List<AppManager.App>) data.next();
    }
}
