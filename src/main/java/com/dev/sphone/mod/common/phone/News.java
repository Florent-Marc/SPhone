package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public long getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        id = (int) objects[0];
        title = (String) objects[0];
        content = (String) objects[1];
        image = (String) objects[2];
        date = (long) objects[3];
        author = (String) objects[4];
    }
}
