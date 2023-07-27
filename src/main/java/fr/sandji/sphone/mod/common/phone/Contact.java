
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

import java.util.Objects;

public class Contact implements ISerializable, ISerializablePacket {

    int id;
    String name;
    String lastname;
    String numero;
    String notes;

    public Contact() {
    }

    public Contact(int id, String name, String lastname, String numero, String notes) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.numero = numero;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getNumero() {
        return numero;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{id, name, lastname, numero, notes};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        id = (int) objects[0];
        name = (String) objects[1];
        lastname = (String) objects[2];
        numero = (String) objects[3];
        notes = (String) objects[4];
    }

    public boolean equals(Contact obj) {
        return this.name.equals(obj.name) && Objects.equals(this.numero, obj.numero);
    }
}
