package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

import java.util.List;

public class Conversation implements ISerializable, ISerializablePacket {

    private List<Message> messages;
    private Message lastMessage;
    private Contact sender;

    public Conversation() {
    }

    public Conversation(List<Message> messages, Contact sender, Message lastMessage) {
        this.messages = messages;
        this.sender = sender;
        this.lastMessage = lastMessage;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Contact getSender() {
        return sender;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setSender(Contact sender) {
        this.sender = sender;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{messages, sender,lastMessage};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        messages = (List<Message>) objects[0];
        sender = (Contact) objects[1];
        lastMessage = (Message) objects[2];

    }
}
