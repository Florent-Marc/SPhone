package com.dev.sphone.mod.server.bdd;

import com.dev.sphone.SPhone;
import com.dev.sphone.api.events.DatabaseBuildEvent;
import com.dev.sphone.mod.server.bdd.sql.MySQL;
import com.dev.sphone.mod.server.bdd.sqlite.SQLite;
import com.dev.sphone.mod.utils.exceptions.DatabaseException;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MethodesBDDImpl {

    public static DatabaseType DatabaseInstance;

    static HashMap<String, Class<? extends DatabaseType>> DATABASES_TYPES = new HashMap<>();

    public static void init() throws DatabaseException {
        Properties props = new Properties();

        MinecraftForge.EVENT_BUS.post(new DatabaseBuildEvent.Pre(DATABASES_TYPES));


        try {
            props.load(new FileReader("bdd.properties"));
//            if(props.getProperty("dbtype").equals("mysql")) {
//                DatabaseInstance = new MySQL();
//            }
//            if(props.getProperty("dbtype").equals("sqlite")) {
//                DatabaseInstance = new SQLite();
//            }

            DATABASES_TYPES.put("mysql", MySQL.class);
            DATABASES_TYPES.put("sqlite", SQLite.class);

            DATABASES_TYPES.forEach((name, type) -> {
                if(props.getProperty("dbtype").equals(name)) {
                    try {
                        DatabaseInstance = type.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            SPhone.logger.fatal("Can't load bdd.properties file");
            e.printStackTrace();
        }

        try {
            DatabaseInstance.prepapreDatabase();
        } catch (DatabaseException e) {
            SPhone.logger.fatal("/!\\ SPhone can't connect to database. Logs :  /!\\");
            e.printStackTrace();
        }


    }

    public static void checkFile() {
        if (!new File("bdd.properties").exists()) {
            try {

                File f = new File("bdd.properties");
                boolean fileCt = f.createNewFile();
                if(!fileCt) {
                    SPhone.logger.fatal("Can't create bdd.properties file");
                    return;
                }
                FileWriter fw = new FileWriter(f);
                fw.write("dbtype=mysql\n");
                fw.write("url=jdbc:mysql://127.0.0.1:3306/phone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true\n");
                fw.write("user=root\n");
                fw.write("password=\n");
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static DatabaseType getDatabaseInstance() {
        return DatabaseInstance;
    }
}
