package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

import java.util.List;

public class Conversation implements ISerializable, ISerializablePacket {

    private List<Message> messages;
    private long lastUpdate;
    private Contact sender;

    public Conversation() {
    }

    public Conversation(List<Message> messages, long lastUpdate, Contact sender) {
        this.messages = messages;
        this.lastUpdate = lastUpdate;
        this.sender = sender;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public Contact getSender() {
        return sender;
    }


    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{messages, lastUpdate, sender};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        messages = (List<Message>) objects[0];
        lastUpdate = (long) objects[1];
        sender = (Contact) objects[2];

    }
}
