package com.dev.sphone.mod.server.bdd.sqlite;

import com.dev.sphone.SPhone;
import com.dev.sphone.mod.server.bdd.DatabaseType;
import com.dev.sphone.mod.server.bdd.QueryResult;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class SQLite implements DatabaseType {
    private static String url;
    private static Connection c;
    private static Properties props = new Properties();


    public SQLite() {
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

        SPhone.logger.info("SQLITE URL: " + url);
    }

    @Override
    public DatabaseType getInstance() throws DatabaseException {
        if (c == null) {
            try {
                c = DriverManager.getConnection(url);
            } catch (SQLException e) {
                throw new DatabaseException("Cannot connect to database.", e);
            }
        }
        return this;
    }

    @Override
    public void execute(String query) {
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

    @Override
    public void execute(String query, Object... args) {
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

    @Override
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

    @Override
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
    public void checktables() {
        DatabaseType instance = null;
        try {
            instance = getInstance();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
        instance.execute("CREATE TABLE \"contact\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"lastname\"\tTEXT,\n" +
                "\t\"numero\"\tTEXT,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");

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
        instance.execute("CREATE TABLE \"sim\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"number\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        instance.execute("CREATE TABLE \"notes\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"note\"\tTEXT,\n" +
                "\t\"date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        instance.execute("CREATE TABLE \"news_accounts\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"sim\"\tTEXT,\n" +
                "\t\"username\"\tTEXT,\n" +
                "\t\"password\"\tTEXT,\n" +
                "\t\"creation_date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
        instance.execute("CREATE TABLE \"news\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"content\"\tTEXT,\n" +
                "\t\"image\"\tTEXT,\n" +
                "\t\"date\"\tINTEGER,\n" +
                "\t\"author\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ")");
    }
}
