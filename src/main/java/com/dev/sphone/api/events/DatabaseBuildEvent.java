package com.dev.sphone.api.events;

import com.dev.sphone.mod.common.phone.Message;
import com.dev.sphone.mod.server.bdd.DatabaseType;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.HashMap;
import java.util.List;


public class DatabaseBuildEvent extends Event {

    public DatabaseBuildEvent() {
    }

    public static class Pre extends DatabaseBuildEvent {

        private final HashMap<String, Class<? extends DatabaseType>> databasesTypes;

        public Pre(HashMap<String, Class<? extends DatabaseType>> databasesTypes) {
            this.databasesTypes = databasesTypes;
        }

        public HashMap<String, Class<? extends DatabaseType>> getDatabasesTypes() {
            return databasesTypes;
        }

        public void addDatabaseType(String name, Class<? extends DatabaseType> type) {
            databasesTypes.put(name, type);
        }
    }

    public static class Post extends DatabaseBuildEvent {

        private final DatabaseType instance;

        public Post(DatabaseType instance) {
            this.instance = instance;
        }

        public DatabaseType getInstance() {
            return instance;
        }
    }
}
