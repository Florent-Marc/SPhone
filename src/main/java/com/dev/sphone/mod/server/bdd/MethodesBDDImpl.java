package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.mod.common.phone.Contact;
import com.dev.sphone.mod.common.phone.Conversation;
import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.common.phone.Note;
import com.dev.sphone.mod.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MethodesBDDImpl {

    static SQLUtils instance = new SQLUtils();

    public static void checkFile() {
        if (!new File("bdd.properties").exists()) {
            if (!new File("bdd.properties").exists()) {
                try {
                    File f = new File("bdd.properties");
                    f.createNewFile();
                    //write default values url, user, password
                    FileWriter fw = new FileWriter(f);
                    fw.write("url=jdbc:mysql://127.0.0.1:3306/phone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true\n");
                    fw.write("user=root\n");
                    fw.write("password=\n");
                    fw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void checkTable() {
        instance.execute("CREATE TABLE IF NOT EXISTS `contact` (\n" +
                "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` TEXT NOT NULL DEFAULT NULL,\n" +
                "\t`name` INT NULL DEFAULT NULL,\n" +
                "\t`lastname` INT NULL DEFAULT NULL,\n" +
                "\t`numero` INT NULL DEFAULT NULL,\n" +
                "\t`note` INT NULL DEFAULT NULL,\n" +
                "\tPRIMARY KEY (`id`)\n" +
                ")\n" +
                ";\n");
        instance.execute("CREATE TABLE IF NOT EXISTS `message` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sender` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`receiver` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`message` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`date` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "ENGINE=InnoDB\n" +
                ";");
        instance.execute("CREATE TABLE IF NOT EXISTS `sim` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` TEXT NOT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`number` TEXT NOT NULL COLLATE 'utf8_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=452\n" +
                ";\n");
        instance.execute("CREATE TABLE IF NOT EXISTS notes (\n" +
                "  id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "  sim VARCHAR(10),\n" +
                "  name VARCHAR(64),\n" +
                "  note TEXT,\n" +
                "  date BIGINT(255)\n" +
                ");");
    }

    public static void addContact(int sim, Contact contact) {
        instance.execute("INSERT INTO contact (sim, name, lastname, numero, note) VALUES (?, ?, ?, ?, ?)", sim, contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes());
    }

    public static void editContact(Contact contact) {
        instance.execute("UPDATE contact SET name = ?, lastname = ?, numero = ?, note = ? WHERE id = ?", contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getId());
    }

    public static void deleteContact(Contact contact) {
        instance.execute("DELETE FROM contact WHERE id = ?", contact.getId());
    }


    public static List<Contact> getContacts(int sim) {
        List<Contact> contacts = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM contact WHERE sim = ?", sim);
        if(qr != null) {
            for (int i = 0; i < qr.getRowsCount(); i++) {
                contacts.add(new Contact(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4), qr.getValue(i, 5)));
            }
        }
        return contacts;
    }

    public static List<Note> getNotes(int sim) {
        List<Note> notes = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM notes WHERE sim = ?", sim);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            //get the current date in a long format
            notes.add(
                    new Note(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), Long.parseLong(qr.getValue(i, 4)) )
            );
        }
        return notes;
    }

    public static void addNote(int sim, Note note) {
        instance.execute("INSERT INTO notes (sim, name, note, date) VALUES (?, ?, ?, ?)", sim, note.getTitle(), note.getText(), note.getDate());
    }
    public static void editNote(Note note) {
        instance.execute("UPDATE notes SET name = ?, note = ?, date = ? WHERE id = ?", note.getTitle(), note.getText(), note.getDate(), note.getId());
    }

    public static void deleteNote(Note note) {
        instance.execute("DELETE FROM notes WHERE id = ?", note.getId());
    }

    public static void addMessage(Message message) {
        instance.execute("INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?)", message.getSender(), message.getReceiver(), message.getMessage(), message.getDate());
    }

    public static int getNumero(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        if(qr.getRowsCount() > 0) {
            return Integer.parseInt(qr.getValue(0, 2));
        }
        return -1;
    }

    public static List<Message> getMessages(int sim) {
        int number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM message WHERE sender = ? OR receiver = ?", number, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }
    public static List<Message> getMessage(int sim, String contactNumber) {
        int number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)", number, contactNumber, contactNumber, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }


    public static Conversation getConversation(int sim, Contact contact) {

        List<Message> messages = getMessage(sim, contact.getNumero());
        messages.sort(Comparator.comparing(o -> Utils.getDate(o.getDate())));

        if (messages.size() == 0)
            return new Conversation(messages, contact, new Message("", 0, "", ""));
        return new Conversation(messages, contact, messages.get(messages.size() - 1));

    }


    public static List<Conversation> getConversations(int sim) {
        List<Conversation> conversations = new ArrayList<>();
        List<String> senders = new ArrayList<>();
        List<Message> messages = getMessages(sim);
        for (Message message : messages) {
            if (!senders.contains(message.getSender()) || !senders.contains(message.getReceiver())) {
                senders.add(message.getSender());
                senders.add(message.getReceiver());

                List<Message> messages1 = messages.stream().filter(m -> (m.getSender().equals(message.getSender()) && m.getReceiver().equals(message.getReceiver())) || (m.getSender().equals(message.getReceiver()) && m.getReceiver().equals(message.getSender()))).collect(Collectors.toList());

                messages1.sort(Comparator.comparing(o -> Utils.getDate(o.getDate())));

                String firstSenderNumber = (!Objects.equals(message.getSender(), String.valueOf(getNumero(sim))) ? message.getSender() : message.getReceiver());
                if (getContacts(sim).stream().anyMatch(c -> c.getNumero().equals(firstSenderNumber))) {
                    Contact contact = getContacts(sim).stream().filter(c -> c.getNumero().equals(firstSenderNumber)).findFirst().get();
                    conversations.add(new Conversation(messages1, contact, messages1.get(messages1.size() - 1)));
                } else {
                    conversations.add(new Conversation(messages1, new Contact(-1, firstSenderNumber, "", firstSenderNumber, ""), messages1.get(messages1.size() - 1)));
                }
            }
        }
        return conversations;
    }

    public static boolean checkNumber(int number) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE number = ?", number);
        return qr.getRowsCount() != 0;
    }

    public static boolean checkSim(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return qr.getRowsCount() != 0;
    }

    public static boolean addSim(int sim, int number) {
        if (!checkNumber(number) && !checkSim(sim)) {
            instance.execute("INSERT INTO sim (sim, number) VALUES (?, ?)", sim, number);
            return true;
        }
        return false;
    }

}
