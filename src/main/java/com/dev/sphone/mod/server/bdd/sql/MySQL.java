package com.dev.sphone.mod.server.bdd.sql;



import com.dev.sphone.mod.server.bdd.DatabaseType;
import com.dev.sphone.mod.server.bdd.QueryResult;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

public class MySQL implements DatabaseType {
    private static String url, log, pwd;
    private static Connection c;
    private static Properties props = new Properties();


    public MySQL(){
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
        System.out.println("using url: " + url);
        System.out.println("using log: " + log);
        System.out.println("using pwd: " + pwd);
    }

    public MySQL getInstance(){
        if(c == null){
            try{
                c = DriverManager.getConnection(url, log,pwd);
            } catch (SQLException e) {
                return null;
            }
        }
        return this;
    }


    public void execute(String query){
        getInstance();
        Statement s = null;
        try {
            if(Objects.isNull(c)) {
                throw new DatabaseException("Database connection is null, Please check is the database is running. (c is null.)");
            }
            s = c.createStatement();
            if(s == null){
                throw new IllegalArgumentException("s is null silly");
            }
            s.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

    }

    public void execute(String query, Object... args){
        getInstance();
        PreparedStatement s = null;
        try{
            s= c.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for (int i = 1; i <= args.length; i++) {
                s.setString(i, args[i-1].toString());
            }
            s.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public QueryResult getData(String query){
        getInstance();
        Statement s = null;
        ResultSet r = null;
        try {
            s = c.createStatement();
            if(s == null){
                throw new IllegalArgumentException("s is null silly");
            }
            r = s.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s,r);
    }

    //Ne pas s'en servir maintenant
    public QueryResult getData(String query, Object... args){
        getInstance();
        PreparedStatement s =null;
        ResultSet r = null;
        try{
            s = c.prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try{
            for (int i = 0; i < args.length; i++) {
                s.setString(i+1, args[i].toString());

            }
            r = s.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new QueryResult(s, r);

    }

    @Override
    public void checktables() throws DatabaseException {
        DatabaseType instance = null;
        instance = getInstance();

        if(instance == null){
            throw new DatabaseException("Database connection is null, Please check is the database is running. (instance is null.)");
        }

        instance.execute("CREATE TABLE IF NOT EXISTS `contact` (\n" +
                "\t`id` INT(10) NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`name` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`lastname` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`numero` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\t`note` TEXT NULL DEFAULT NULL COLLATE 'utf8mb3_general_ci',\n" +
                "\tPRIMARY KEY (`id`) USING BTREE\n" +
                ")\n" +
                "COLLATE='utf8mb3_general_ci'\n" +
                "ENGINE=InnoDB\n" +
                "AUTO_INCREMENT=0\n" +
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
                "AUTO_INCREMENT=0\n" +
                ";\n");
        instance.execute("CREATE TABLE IF NOT EXISTS `notes` (\n" +
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
        instance.execute("CREATE TABLE IF NOT EXISTS `news_accounts` (\n" +
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
        instance.execute("CREATE TABLE IF NOT EXISTS `news` (\n" +
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
    }


    /* on garde ça au cas ou ça marche pô
    public static Connection connectionbdd() {
            try {
                System.out.println(TextFormatting.DARK_GREEN + urlbase+host+"/"+database+utilisateur+mdp);
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://" + host + "/" + database ;
                connexion = DriverManager.getConnection(url, utilisateur, mdp);
                System.out.println(TextFormatting.DARK_GREEN + "Base de donné connecté !");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                System.out.println(e.getCause());
            }
            return connexion;
    }
    public static Boolean checkconnect() {
        java.sql.Connection connection = null;
        return  connection != null;
    }
    */

}