package fr.sandji.sphone.mod.server.bdd;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryResult {
    private Statement s;
    private ResultSet r;
    private int colsCount;
    private List<String[]> result;
    private List<String> colsNames;
    public QueryResult(Statement s, ResultSet r) {
        this.s = s;
        this.r = r;
        try {
            initArrays();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initArrays() throws SQLException {
        ResultSetMetaData metaData = r.getMetaData();
        colsCount = metaData.getColumnCount();
        result = new ArrayList<>();
        colsNames = new ArrayList<>();
        while(r.next()){
            String[] row = new String[colsCount];
            for (int i = 1; i <= colsCount ; i++) {
                Object o = r.getObject(i);
                row[i - 1] = (o == null) ? null : o.toString();
            }
            result.add(row);
        }
        for (int i = 1; i <= colsCount; i++) {
            colsNames.add(metaData.getColumnName(i));
        }
    }

    public List<String[]> getResultAsArray(){
        return result;
    }

    public int getRowsCount(){
        return result.size();
    }

    //ligne du tableau
    public String[] getRow(int row){
        if(row < 0 || row > result.size()){
            throw new IndexOutOfBoundsException("La ligne "+ row+ " est en dehors des limites du résultat (0 - "+result.size()+")!");
        }
        return result.get(row);
    }
    //get une valeur avec une pos
    public String getValue(int ligne, int column){
        if(column < 0 || column > colsCount){
            throw new IndexOutOfBoundsException("La colonne "+ column+ " est en dehors des limites du résultat (0 - "+colsCount+")!");
        }
        return getRow(ligne)[column];
    }

    public String getValue(int row, String column){
        return getValue(row, getColumnIdByName(column));
    }

    public String getColumnName(int column){
        if(column < 0 || column > colsCount){
            throw new IndexOutOfBoundsException("La colonne "+ column+ " est en dehors des limites du résultat (0 - "+colsCount+")!");
        }
        return colsNames.get(column);
    }

    public int getColumnIdByName(String name){
        for (int i = 0; i <colsNames.size(); i++) {
            if(name.equals(colsNames.get(i))){
                return i;
            }
        }
        throw new IllegalArgumentException("Le résultat ne contient aucune colonne nommée '"+name+"'!");
    }

    public Statement getStatement() {
        return s;
    }

    public ResultSet getResult() {
        return r;
    }

    public void close(){
        try {
            s.close();
            r.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
