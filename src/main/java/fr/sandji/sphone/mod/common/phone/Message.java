package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Message implements ISerializable, ISerializablePacket {

    private String message;
    private long date;
    private int sender;
    private int receiver;

    public Message() {
        //this("Called default constructor", 0, new Contact(), new Contact());
    }

    public Message(String message, long date, int sender, int receiver) {
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }


    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{message, date, sender, receiver};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        message = (String) objects[0];
        date = (long) objects[1];
        sender = (int) objects[2];
        receiver = (int) objects[3];

    }
}
