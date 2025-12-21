package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

public class Message implements ISerializable, ISerializablePacket {

    private String message;
    private long date;
    private String sender;
    private String receiver;

    public Message() {}

    public Message(String message, long date, String sender, String receiver) {
        this.message = message;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage() { return message; }
    public long getDate() { return date; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{message, date, sender, receiver};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        message = (String) data.next();
        date = (long) data.next();
        sender = (String) data.next();
        receiver = (String) data.next();
    }
}
