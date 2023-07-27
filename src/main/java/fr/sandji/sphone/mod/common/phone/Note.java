package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Note implements ISerializable, ISerializablePacket {
    private String title;
    private String text;
    private long date;
    private int id;

    public Note() {}

    public Note(int id, String title, String text, long date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public long getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{title, text, date, id};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        title = (String) objects[0];
        text = (String) objects[1];
        date = (long) objects[2];
        id = (int) objects[3];
    }
}
