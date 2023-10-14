package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.mod.utils.exceptions.DatabaseException;

public interface DatabaseType {
    DatabaseType getInstance() throws DatabaseException;
    void execute(String query);
    void execute(String query, Object... args);
    QueryResult getData(String query);
    QueryResult getData(String query, Object... args);
    void checktables();
}
