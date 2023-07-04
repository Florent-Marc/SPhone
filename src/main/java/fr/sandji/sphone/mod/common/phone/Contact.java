
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
    String player_associated;

    public Contact() {}

    public Contact(String name, String lastname, int numero, String notes, String player_associated) {
        this.name = name;
        this.lastname = lastname;
        this.numero = numero;
        this.notes = notes;
        this.player_associated = player_associated;
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

    public String getPlayer_associated() {
        return player_associated;
    }

    public void setPlayer_associated(String player_associated) {
        this.player_associated = player_associated;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public Object[] getObjectsToSave() {
        return new Object[]{name, lastname, numero, notes, player_associated};
    }

    @Override
    public void populateWithSavedObjects(Object[] objects) {
        name = (String) objects[0];
        lastname = (String) objects[1];
        numero = (Integer) objects[2];
        notes = (String) objects[3];
        player_associated = (String) objects[4];
    }
}
