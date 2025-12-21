package com.dev.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;
import fr.aym.acslib.utils.DeserializedData;

import java.util.Objects;

public class Contact implements ISerializable, ISerializablePacket {

    int id;
    String name;
    String lastname;
    String numero;
    String notes;
    String photo;

    public Contact() {}

    public Contact(int id, String name, String lastname, String numero, String notes, String photo) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.numero = numero;
        this.notes = notes;
        this.photo = photo;
    }

    public Contact(int id, String name, String lastname, String numero, String notes) {
        this(id, name, lastname, numero, notes, "empty");
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLastname() { return lastname; }
    public String getNumero() { return numero; }
    public String getNotes() { return notes; }
    public String getPhoto() { return photo; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setPhoto(String photo) { this.photo = photo; }

    @Override
    public int getVersion() { return 1; }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{id, name, lastname, numero, notes, photo};
    }

    @Override
    public void populateWithSavedObjects(DeserializedData data) {
        id = (int) data.next();
        name = (String) data.next();
        lastname = (String) data.next();
        numero = (String) data.next();
        notes = (String) data.next();
        photo = (String) data.next();
    }

    public boolean equals(Contact obj) {
        return this.name.equals(obj.name) && Objects.equals(this.numero, obj.numero);
    }
}
