package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

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

    public String getTitle() { return title; }
    public String getText() { return text; }
    public long getDate() { return date; }
    public int getId() { return id; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{title, text, date, id};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        title = (String) data.next();
        text = (String) data.next();
        date = (long) data.next();
        id = (int) data.next();
    }
}
