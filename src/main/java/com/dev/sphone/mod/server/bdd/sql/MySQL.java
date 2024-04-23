package com.dev.sphone.mod.server.bdd.sql;


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

public class MySQL implements DatabaseType {
    private String url, log, pwd;
    private Connection connection;
    private Properties props = new Properties();


    public MySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            props.load(new FileReader(new File("bdd.properties")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        url = props.getProperty("url");
        log = props.getProperty("user");
        pwd = props.getProperty("password");
    }



    @Override
    public DatabaseType getInstance() throws DatabaseException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, log, pwd);
            } catch (SQLException e) {
                return null;
            }
        }
        return this;
    }

    public void execute(String query) throws DatabaseException {
        getInstance();
        Statement s = null;
        try {
            if (Objects.isNull(connection)) {
                throw new DatabaseException("Database connection is null, Please check is the database is running. (c is null.)");
            }
            s = connection.createStatement();

            if (s == null) {
                throw new IllegalArgumentException("s is null silly");
            }
            s.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

    }

    public void execute(String query, Object... args) throws DatabaseException {
        getInstance();
        PreparedStatement s = null;
        try {
            s = connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (int i = 1; i <= args.length; i++) {
                s.setString(i, args[i - 1].toString());
            }
            s.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public QueryResult getData(String query) throws DatabaseException {
        getInstance();
        Statement s = null;
        ResultSet r = null;
        try {
            s = connection.createStatement();
            if (s == null) {
                throw new IllegalArgumentException("s is null silly");
            }
            r = s.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s, r);
    }

    //Ne pas s'en servir maintenant
    public QueryResult getData(String query, Object... args) throws DatabaseException {
        getInstance();
        PreparedStatement s = null;
        ResultSet r = null;
        try {
            s = connection.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (int i = 0; i < args.length; i++) {
                s.setString(i + 1, args[i].toString());

            }
            r = s.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s, r);

    }

    @Override
    public void prepapreDatabase() throws DatabaseException {
        if (this.connection == null) {
            try {
                this.connection = DriverManager.getConnection(url, log,pwd);
            } catch (SQLException e) {
                throw new DatabaseException("Cannot connect to database. Used URL : " + url, e);
            }
        }
//        this.connection = getInstance();


        if (this.connection == null) {
            throw new DatabaseException("Database connection is null, Please check is the database is running. (this is null.)");
        }

        this.execute("CREATE TABLE IF NOT EXISTS `contact` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`name` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`lastname` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`numero` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`note` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`photo` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8mb3_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
                ";\n");

        this.execute("CREATE TABLE IF NOT EXISTS `message` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sender` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`receiver` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`message` TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`date` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "ENGINE=InnoDB\n" +
                ";");
        this.execute("CREATE TABLE IF NOT EXISTS `sim` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` TEXT NOT NULL COLLATE 'utf8_general_ci',\n" +
                "\t`number` TEXT NOT NULL COLLATE 'utf8_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
                ";\n");
        this.execute("CREATE TABLE IF NOT EXISTS `notes` (\n" +
                "\t`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`name` VARCHAR(24) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`note` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`date` BIGINT(19) NULL DEFAULT NULL,\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8mb3_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
                ";\n");
        this.execute("CREATE TABLE IF NOT EXISTS `news_accounts` (\n" +
                "\t`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`username` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`password` VARCHAR(100) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`creation_date` BIGINT(19) NULL DEFAULT NULL,\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8mb3_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
                ";\n");
        this.execute("CREATE TABLE IF NOT EXISTS `news` (\n" +
                "\t`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "\t`title` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`content` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`image` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`date` BIGINT(19) NULL DEFAULT NULL,\n" +
                "\t`author` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8mb3_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
                ";\n");

        DatabaseMetaData metadata = null;
        try {
            metadata = connection.getMetaData();

            ResultSet resultSet = metadata.getColumns(null, null, "contact", "photo");
            if (!resultSet.next()) {
                System.out.println("Column photo doesn't exist in contact table");
                this.execute("ALTER TABLE contact ADD photo TEXT NULL DEFAULT NULL COLLATE 'utf8_general_ci';"); // after photo update
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addContact(int sim, Contact contact) {
        try {
            this.execute("INSERT INTO contact (sim, name, lastname, numero, note, photo) VALUES (?, ?, ?, ?, ?, ?)", sim, contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getPhoto());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    public void editContact(Contact contact) {
        try {
            this.execute("UPDATE contact SET name = ?, lastname = ?, numero = ?, note = ?, photo = ? WHERE id = ?", contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes(), contact.getPhoto(), contact.getId());
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
        try {
            qr = this.getData("SELECT * FROM contact WHERE sim = ?", sim);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        try {
            qr = this.getData("SELECT * FROM notes WHERE sim = ?", sim);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        try {
            qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 2);
        }
        return "";
    }

    public String getSimFromNum(String num) {
        QueryResult qr = null;
        try {
            qr = this.getData("SELECT * FROM sim WHERE number = ?", num);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        if (qr.getRowsCount() > 0) {
            return qr.getValue(0, 1);
        }
        return null;
    }


    public List<Message> getMessages(int sim) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = null;
        try {
            qr = this.getData("SELECT * FROM message WHERE sender = ? OR receiver = ?", number, number);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 1), qr.getValue(i, 2)));
        }
        return messages;
    }

    public List<Message> getMessage(int sim, String contactNumber) {
        String number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = null;
        try {
            qr = this.getData("SELECT * FROM message WHERE (sender = ? AND receiver = ?) OR (sender = ? AND receiver = ?)", number, contactNumber, contactNumber, number);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        try {
            qr = this.getData("SELECT * FROM sim WHERE number = ?", number);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return qr.getRowsCount() != 0;
    }

    public boolean checkSim(int sim) {
        QueryResult qr = null;
        try {
            qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
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
        try {
            qr = this.getData("SELECT * FROM news");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < qr.getRowsCount(); i++) {
            news.add(
                    new News(Integer.parseInt(qr.getValue(i, 0)), qr.getValue(i, 1), qr.getValue(i, 2), qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), qr.getValue(i, 5))
            );
        }
        return news;
    }

    public boolean isSimExist(int sim) {
        QueryResult qr = null;
        try {
            qr = this.getData("SELECT * FROM sim WHERE sim = ?", sim);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        return qr.getRowsCount() != 0;
    }

}