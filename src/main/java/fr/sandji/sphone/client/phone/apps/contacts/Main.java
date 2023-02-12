package fr.sandji.sphone.client.phone.apps.contacts;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ContactManager.loadContacts();
        ContactManager.displayContacts();

        /*Scanner sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastname = sc.nextLine();
        System.out.print("Enter phone number: ");
        int phone_number = sc.nextInt();
        System.out.print("Enter notes: ");
        sc.nextLine(); // Pour consommer le retour à la ligne restant après nextInt()
        String notes = sc.nextLine();

        ContactManager.Contact contact = new ContactManager.Contact(phone_number, name, lastname, notes);
        ContactManager.addContact(contact);
        ContactManager.saveContacts();*/
    }
    /*public static void main(String[] args) {
        ContactManager.loadContacts();
        List<ContactManager.Contact> contacts = ContactManager.getContacts();

        Collections.sort(contacts, (a, b) -> a.name.compareTo(b.name));

        System.out.println("Contacts (sorted by name):");
        for (ContactManager.Contact contact : contacts) {
            System.out.println("- " + contact.name + " " + contact.lastname + ": " + contact.phone_number + " (" + contact.notes + ")");
        }
    }*/
}


