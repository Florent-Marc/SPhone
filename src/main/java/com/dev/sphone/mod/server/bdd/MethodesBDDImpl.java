package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.phone.*;
import com.dev.sphone.mod.server.bdd.sql.MySQL;
import com.dev.sphone.mod.server.bdd.sqlite.SQLite;
import com.dev.sphone.mod.utils.UtilsServer;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MethodesBDDImpl {

    static DatabaseType instance;

    public static void init() throws DatabaseException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("bdd.properties"));

            if(props.getProperty("dbtype").equals("mysql")) {
                instance = new MySQL();
            }
            if(props.getProperty("dbtype").equals("sqlite")) {
                instance = new SQLite();
            }
        } catch (IOException e) {
            SPhone.logger.fatal("Can't load bdd.properties file");
            e.printStackTrace();
        }


    }

    public static void checkFile() {
        if (!new File("bdd.properties").exists()) {
            try {

                File f = new File("bdd.properties");
                boolean fileCt = f.createNewFile();
                if(!fileCt) {
                    SPhone.logger.fatal("Can't create bdd.properties file");
                    return;
                }
                FileWriter fw = new FileWriter(f);
                fw.write("dbtype=mysql");
                fw.write("url=jdbc:mysql://127.0.0.1:3306/phone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true\n");
                fw.write("user=root\n");
                fw.write("password=\n");
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void checkTable() throws DatabaseException {
        instance.checktables();
    }

    public static void addContact(int sim, Contact contact) {
        instance.execute("INSERT INTO contact (sim, name, lastname, numero, note, photo) VALUES (?, ?, ?, ?, ?, ?)", sim, contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getPhoto());
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
        if (qr != null) {
            for (int i = 0; i < qr.getRowsCount(); i++) {
                contacts.add(new Contact(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4), qr.getValue(i, 5), qr.getValue(i, 6)));
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
                    new Note(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), Long.parseLong(qr.getValue(i, 4)))
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

    public static String getNumero(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 2);
        }
        return "";
    }

    public static String getNumeroFromNumber(int number) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE number = ?", number);
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 2);
        }
        return null;
    }


    public static List<Message> getMessages(int sim) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM message WHERE sender = ? OR receiver = ?", number, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }

    public static List<Message> getMessage(int sim, String contactNumber) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)", number, contactNumber, contactNumber, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }


    public static Conversation getConversation(int sim, Contact contact) {

        List<Message> messages = getMessage(sim, contact.getNumero());
        messages.sort(Comparator.comparing(o -> UtilsServer.getDate(o.getDate())));

        if (messages.size() == 0)
            return new Conversation(messages, contact);
        return new Conversation(messages, contact);

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

                messages1.sort(Comparator.comparing(o -> UtilsServer.getDate(o.getDate())));

                String firstSenderNumber = (!Objects.equals(message.getSender(), String.valueOf(getNumero(sim))) ? message.getSender() : message.getReceiver());
                if (getContacts(sim).stream().anyMatch(c -> c.getNumero().equals(firstSenderNumber))) {
                    Contact contact = getContacts(sim).stream().filter(c -> c.getNumero().equals(firstSenderNumber)).findFirst().get();
                    conversations.add(new Conversation(messages1, contact));
                } else {
                    conversations.add(new Conversation(messages1, new Contact(-1, firstSenderNumber, "", firstSenderNumber, "")));
                }
            }
        }
        return conversations;
    }

    public static boolean checkNumber(String number) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE number = ?", number);
        return qr.getRowsCount() != 0;
    }

    public static boolean checkSim(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return qr.getRowsCount() != 0;
    }

    public static boolean addSim(int sim, String number) {
        if (!checkNumber(number) && !checkSim(sim)) {
            instance.execute("INSERT INTO sim (sim, number) VALUES (?, ?)", sim, number);
            return true;
        }
        return false;
    }

    public static void addNews(News news) {
        instance.execute("INSERT INTO news (title, content, image, date, author) VALUES (?, ?, ?, ?, ?)", news.getTitle(), news.getContent(), news.getImage(), news.getDate(), news.getAuthor());
    }

    public static void editNews(News news) {
        instance.execute("UPDATE news SET title = ?, content = ?, image = ?, date = ?, author = ? WHERE id = ?", news.getTitle(), news.getContent(), news.getImage(), news.getDate(), news.getAuthor(), news.getId());
    }

    public static void deleteNews(News news) {
        instance.execute("DELETE FROM news WHERE id = ?", news.getId());
    }

    public static List<News> getNews() {
        List<News> news = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM news");
        for (int i = 0; i < qr.getRowsCount(); i++) {
            news.add(
                    new News(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 5))
            );
        }
        return news;
    }

}
