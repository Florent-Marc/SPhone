package com.dev.sphone.mod.server.bdd;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SQLUtils {
    private static String url, log, pwd;
    private static Connection c;
    private static Properties props = new Properties();


    public SQLUtils(){
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

    public SQLUtils getInstance(){
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
            s = c.createStatement();
            if(s == null){
                throw new IllegalArgumentException("s is null silly");
            }
            s.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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