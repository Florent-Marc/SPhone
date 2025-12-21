package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

import java.util.List;

public class Conversation implements ISerializable, ISerializablePacket {

    private List<Message> messages;
    private Contact sender;

    public Conversation() {}

    public Conversation(List<Message> messages, Contact sender) {
        this.messages = messages;
        this.sender = sender;
    }

    public List<Message> getMessages() { return messages; }
    public Contact getSender() { return sender; }
    public void setMessages(List<Message> messages) { this.messages = messages; }
    public void setSender(Contact sender) { this.sender = sender; }

    @Override
    public int getVersion() { return 0; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{messages, sender};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        messages = (List<Message>) data.next();
        sender = (Contact) data.next();
    }

    public void addMessage(Message message) { messages.add(message); }
    public Message getLastMessage() { return messages.get(messages.size() - 1); }
}
