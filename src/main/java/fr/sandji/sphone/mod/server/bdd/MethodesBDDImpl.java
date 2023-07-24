package fr.sandji.sphone.mod.server.bdd;

import fr.sandji.sphone.mod.common.phone.Contact;
import fr.sandji.sphone.mod.common.phone.Message;

import java.util.ArrayList;
import java.util.List;

public class MethodesBDDImpl {

    static SQLUtils instance = MethodesBDD.instance;
    public static void checkTable() {
        instance.execute("CREATE TABLE IF NOT EXISTS `contact` (\n" +
                "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "\t`sim` INT NOT NULL,\n" +
                "\t`name` INT NULL DEFAULT NULL,\n" +
                "\t`lastname` INT NULL DEFAULT NULL,\n" +
                "\t`numero` INT NULL DEFAULT NULL,\n" +
                "\t`note` INT NULL DEFAULT NULL,\n" +
                "\tPRIMARY KEY (`id`)\n" +
                ")\n" +
                "COLLATE='utf8_general_ci'\n" +
                "ENGINE=InnoDB\n" +
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
                "AUTO_INCREMENT=452\n" +
                ";\n");
        instance.execute("CREATE TABLE IF NOT EXISTS notes (\n" +
                "  id INT,\n" +
                "  sim VARCHAR(10),\n" +
                "  name VARCHAR(10),\n" +
                "  note VARCHAR(255)\n" +
                ");");
    }

    public static void addContact(int sim, Contact contact) {
        instance.execute("INSERT INTO contact (sim, name, lastname, numero, note) VALUES (?, ?, ?, ?, ?)", sim, contact.getName(), contact.getLastname(), contact.getNumero(), contact.getNotes());
    }

    public static List<Contact> getContact(int sim) {
        List<Contact> contacts = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM contact WHERE sim = ?", sim);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            contacts.add(new Contact(qr.getValue(i, 2), qr.getValue(i, 3), Integer.valueOf(qr.getValue(i, 4).toString()), qr.getValue(i, 5)));
        }
        return contacts;
    }

    public static void addMessage(Message message) {
        instance.execute("INSERT INTO message (sender, receiver, message, date) VALUES (?, ?, ?, ?)", message.getSender(), message.getReceiver(), message.getMessage(), message.getDate());
    }

    public static int getNumero(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return Integer.parseInt(qr.getValue(0, 2));
    }

    public static List<Message> getMessage(int sim) {
        int number = getNumero(sim);
        List<Message> messages = new ArrayList<>();
        QueryResult qr = instance.getData("SELECT * FROM message WHERE sender = ? OR receiver = ?", number, number);
        for (int i = 0; i < qr.getRowsCount(); i++) {
            messages.add(new Message(qr.getValue(i, 3), Long.valueOf(qr.getValue(i, 4)), Integer.valueOf(qr.getValue(i, 1)), Integer.valueOf(qr.getValue(i, 2))));
        }
        return messages;
    }

    public static boolean checkNumber(int number) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE number = ?", number);
        return qr.getRowsCount() != 0;
    }

    public static boolean checkSim(int sim) {
        QueryResult qr = instance.getData("SELECT * FROM sim WHERE sim = ?", sim);
        return qr.getRowsCount() != 0;
    }

    public static boolean addSim(int sim, int number) {
        if (!checkNumber(number) && !checkSim(sim)) {
            instance.execute("INSERT INTO sim (sim, number) VALUES (?, ?)", sim, number);
            return true;
        }
        return false;
    }

}
