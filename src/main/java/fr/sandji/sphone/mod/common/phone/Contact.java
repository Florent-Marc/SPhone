
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.common.phone;

import fr.aym.acslib.utils.nbtserializer.ISerializable;
import fr.aym.acslib.utils.packetserializer.ISerializablePacket;

public class Contact implements ISerializable, ISerializablePacket {

    String name;
    String lastname;
    int numero;
    String notes;

    public Contact() {}

    public Contact(String name, String lastname, int numero, String notes) {
        this.name = name;
        this.lastname = lastname;
        this.numero = numero;
        this.notes = notes;
    }
    public Contact(String name, String lastname, int numero) {
        this.name = name;
        this.lastname = lastname;
        this.numero = numero;
        this.notes = "";
    }
    //constructor with name and number
    public Contact(String name, int numero) {
        this.name = name;
        this.numero = numero;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{name, lastname, numero, notes};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        name = (String) objects[0];
        lastname = (String) objects[1];
        numero = (Integer) objects[2];
        notes = (String) objects[3];
    }


    public boolean equals(Contact obj) {
        return this.name.equals(obj.name) && this.numero == obj.numero;
    }
}
