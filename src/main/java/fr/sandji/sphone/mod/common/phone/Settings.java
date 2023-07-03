
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Settings implements ISerializable, ISerializablePacket {

    int background;

    public Settings() {}

    public Settings(int background) {
        this.background = background;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{background};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        background = (Integer) objects[0];
    }

}
