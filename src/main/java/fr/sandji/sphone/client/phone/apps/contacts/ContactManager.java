package fr.sandji.sphone.client.phone.apps.contacts;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ContactManager {
    private static final String FILE_NAME = "phone_contacts.json";

    public static class Contact {
        int phone_number;
        String name;
        String lastname;
        String notes;

        public Contact(int phone_number, String name, String lastname, String notes) {
            this.phone_number = phone_number;
            this.name = name;
            this.lastname = lastname;
            this.notes = notes;
        }
    }

    public static List<Contact> contacts;

    static {
        contacts = new ArrayList<>();
    }

    public static void addContact(Contact contact) {
        contacts.add(contact);
    }

    public static void saveContacts() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(contacts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadContacts() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            contacts = gson.fromJson(reader, new TypeToken<List<Contact>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void displayContacts() {
        System.out.println("List of Contacts:");
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact c1, Contact c2) {
                return c1.name.compareTo(c2.name);
            }
        });
        for (Contact contact : contacts) {
            System.out.println("Name: " + contact.name + " " + contact.lastname + " | Phone Number: " + contact.phone_number + " | Notes: " + contact.notes);
        }
    }


}
