package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

public class Weather implements ISerializable, ISerializablePacket {
    private int clearTime;
    private int rainTime;
    private int thunderTime;
    private boolean isRaining;
    private boolean isThundering;

    public Weather() {}

    public Weather(int clearTime, int rainTime, int thunderTime, boolean isRaining, boolean isThundering) {
        this.clearTime = clearTime;
        this.rainTime = rainTime;
        this.thunderTime = thunderTime;
        this.isRaining = isRaining;
        this.isThundering = isThundering;
    }

    public int getClearTime() { return clearTime; }
    public int getRainTime() { return rainTime; }
    public int getThunderTime() { return thunderTime; }
    public boolean isRaining() { return isRaining; }
    public boolean isThundering() { return isThundering; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{clearTime, rainTime, thunderTime, isRaining, isThundering};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        clearTime = (int) data.next();
        rainTime = (int) data.next();
        thunderTime = (int) data.next();
        isRaining = (boolean) data.next();
        isThundering = (boolean) data.next();
    }
}
