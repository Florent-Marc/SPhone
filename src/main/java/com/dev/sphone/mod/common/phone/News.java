package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

public class News implements ISerializable, ISerializablePacket {

    private int id;
    private String title;
    private String content;
    private String image;
    private long date;
    private String author;

    public News(int id, String title, String content, String image, long date, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.date = date;
        this.author = author;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getImage() { return image; }
    public long getDate() { return date; }
    public String getAuthor() { return author; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{id, title, content, image, date, author};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        id = (int) data.next();
        title = (String) data.next();
        content = (String) data.next();
        image = (String) data.next();
        date = (long) data.next();
        author = (String) data.next();
    }
}
