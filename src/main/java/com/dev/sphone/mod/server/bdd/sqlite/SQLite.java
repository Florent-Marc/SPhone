package com.dev.sphone.mod.server.bdd.sqlite;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.common.phone.*;
import com.dev.sphone.mod.server.bdd.DatabaseType;
import com.dev.sphone.mod.server.bdd.QueryResult;
import com.dev.sphone.mod.utils.UtilsServer;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SQLite implements DatabaseType {
    private static String url;
    private static Connection c;
    private static Properties props = new Properties();


    public SQLite() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            props.load(new FileReader(new File("bdd.properties")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File file = new File("sphone.db");
        url = "jdbc:sqlite:sphone.db";


        c = DriverManager.getConnection(url);

        SPhone.logger.info("SQLITE URL: " + url);
    }

    @Override
    public DatabaseType getInstance() throws DatabaseException {
        return this;
    }

    public void execute(String query) throws DatabaseException {
        try {
            getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        Statement s = null;
        try {
            s = c.createStatement();
            if (s == null) {
                throw new IllegalArgumentException("Statement is null.");
            }
            s.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void execute(String query, Object... args) throws DatabaseException {
        try {
            getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement s = null;
        try {
            s = c.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (int i = 1; i <= args.length; i++) {
                assert s != null;
                s.setString(i, args[i - 1].toString());
            }
            assert s != null;
            s.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public QueryResult getData(String query) {
        try {
            getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        Statement s = null;
        ResultSet r = null;
        try {
            s = c.createStatement();
            if (s == null) {
                throw new IllegalArgumentException("Statement is null.");
            }
            r = s.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s, r);
    }

    public QueryResult getData(String query, Object... args) {
        try {
            getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = c.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (int i = 0; i < args.length; i++) {
                assert s != null;
                s.setString(i + 1, args[i].toString());

            }
            assert s != null;
            r = s.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s, r);
    }

    @Override
    public void prepapreDatabase() throws DatabaseException {
        DatabaseType instance = null;
        try {
            instance = getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        this.execute("CREATE TABLE IF NOT EXISTS \"contact\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"lastname\"\tTEXT,\n" +
                "\t\"numero\"\tTEXT,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\t\"photo\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");

        this.execute("CREATE TABLE IF NOT EXISTS `message` (\n" +
                "\t`id` INTEGER,\n" +
                "\t`sender` TEXT ,\n" +
                "\t`receiver` TEXT,\n" +
                "\t`message` TEXT,\n" +
                "\t`date` TEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")\n" +
                ";");
        this.execute("CREATE TABLE IF NOT EXISTS \"sim\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"number\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        this.execute("CREATE TABLE IF NOT EXISTS \"notes\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        this.execute("CREATE TABLE IF NOT EXISTS \"news_accounts\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"username\"\tTEXT,\n" +
                "\t\"password\"\tTEXT,\n" +
                "\t\"creation_date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        this.execute("CREATE TABLE IF NOT EXISTS \"news\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"content\"\tTEXT,\n" +
                "\t\"image\"\tTEXT,\n" +
                "\t\"date\"\tINTEGER,\n" +
                "\t\"author\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
    }

    @Override
    public void addContact(int sim, Contact contact) {
        try {
            this.execute("INSERT INTO contact (sim, name, lastname, numero, note, photo) VALUES (?, ?, ?, ?, ?, ?)", sim, contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getPhoto());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void editContact(Contact contact) {
        try {
            this.execute("UPDATE contact SET name = ?, lastname = ?, numero = ?, note = ? WHERE id = ?", contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteContact(Contact contact) {
        try {
            this.execute("DELETE FROM contact WHERE id = ?", contact.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Contact> getContacts(int sim) {
        List<Contact> contacts = new ArrayList<>();
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM contact WHERE sim = ?", sim);
        if (qr != null) {
            for (int i = 0; i < qr.getRowsCount(); i++) {
                contacts.add(new Contact(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), qr.getValue(i, 4), qr.getValue(i, 5), qr.getValue(i, 6)));
            }
        }
        return contacts;
    }

    public List<Note> getNotes(int sim) {
        List<Note> notes = new ArrayList<>();
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM notes WHERE sim = ?", sim);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            //get the current date in a long format
            notes.add(
                    new Note(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 2), qr.getValue(i, 3), Long.parseLong(qr.getValue(i, 4)))
            );
        }
        return notes;
    }

    public void addNote(int sim, Note note) {
        try {
            this.execute("INSERT INTO notes (sim, name, note, date) VALUES (?, ?, ?, ?)", sim, note.getTitle(), note.getText(), note.getDate());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void editNote(Note note) {
        try {
            this.execute("UPDATE notes SET name = ?, note = ?, date = ? WHERE id = ?", note.getTitle(), note.getText(), note.getDate(), note.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNote(Note note) {
        try {
            this.execute("DELETE FROM notes WHERE id = ?", note.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void addMessage(Message message) {
        try {
            this.execute("INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?)", message.getSender(), message.getReceiver(), message.getMessage(), message.getDate());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNumero(int sim) {
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 2);
        }
        return "";
    }

    public String getSimFromNum(String num) {
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM sim WHERE number = ?", num);
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 1);
        }
        return null;
    }


    public List<Message> getMessages(int sim) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM message WHERE sender = ? OR receiver = ?", number, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }

    public List<Message> getMessage(int sim, String contactNumber) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)", number, contactNumber, contactNumber, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }


    public Conversation getConversation(int sim, Contact contact) {

        List<Message> messages = getMessage(sim, contact.getNumero());
        messages.sort(Comparator.comparing(o -> UtilsServer.getDate(o.getDate())));

        if (messages.size() == 0)
            return new Conversation(messages, contact);
        return new Conversation(messages, contact);

    }


    public List<Conversation> getConversations(int sim) {
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

    public boolean checkNumber(String number) {
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM sim WHERE number = ?", number);
        return qr.getRowsCount() != 0;
    }

    public boolean checkSim(int sim) {
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return qr.getRowsCount() != 0;
    }

    public boolean addSim(int sim, String number) {
        if (!checkNumber(number) && !checkSim(sim)) {
            try {
                this.execute("INSERT INTO sim (sim, number) VALUES (?, ?)", sim, number);
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    public void addNews(News news) {
        try {
            this.execute("INSERT INTO news (title, content, image, date, author) VALUES (?, ?, ?, ?, ?)", news.getTitle(), news.getContent(), news.getImage(), news.getDate(), news.getAuthor());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void editNews(News news) {
        try {
            this.execute("UPDATE news SET title = ?, content = ?, image = ?, date = ?, author = ? WHERE id = ?", news.getTitle(), news.getContent(), news.getImage(), news.getDate(), news.getAuthor(), news.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNews(News news) {
        try {
            this.execute("DELETE FROM news WHERE id = ?", news.getId());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<News> getNews() {
        List<News> news = new ArrayList<>();
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM news");
        for (int i = 0; i < qr.getRowsCount(); i++) {
            news.add(
                    new News(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 5))
            );
        }
        return news;
    }

    public boolean isSimExist(int sim) {
        QueryResult qr = null;
        qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return qr.getRowsCount() != 0;
    }
}
