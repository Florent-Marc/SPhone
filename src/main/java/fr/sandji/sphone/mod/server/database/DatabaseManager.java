/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod.server.database;

public enum DatabaseManager {
    SPHONE (new DatabaseCredentials("localhost", "root", "", "phone", 3306));

    private DatabaseAccess databaseAccess;
    DatabaseManager(DatabaseCredentials credentials) {
        this.databaseAccess = new DatabaseAccess(credentials);
    }

    public DatabaseAccess getDatabaseAccess() {
        return databaseAccess;
    }

    public static void initAllDatabaseConnections() {
        for (DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.initPool();
        }
    }

    public static void closeAllDatabaseConnections() {
        for (DatabaseManager databaseManager : values()) {
            databaseManager.databaseAccess.closePool();
        }
    }

}