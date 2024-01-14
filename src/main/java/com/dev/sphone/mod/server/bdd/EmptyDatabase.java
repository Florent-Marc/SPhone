package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.mod.utils.exceptions.DatabaseException;

public class EmptyDatabase implements DatabaseType {
    @Override
    public DatabaseType getInstance() throws DatabaseException {
        return null;
    }

    @Override
    public void execute(String query) {

    }

    @Override
    public void execute(String query, Object... args) {

    }

    @Override
    public QueryResult getData(String query) {
        return null;
    }

    @Override
    public QueryResult getData(String query, Object... args) {
        return null;
    }

    @Override
    public void checktables() throws DatabaseException {

    }
}
