package fr.sandji.sphone.mod.server.bdd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class test {

    public static void main(String[] args) {
        //check if file bdd.properties exist
        //if not create it
        if (!new File("bdd.properties").exists()) {
            try {
                File f = new File("bdd.properties");
                f.createNewFile();
                //write default values url, user, password
                FileWriter fw = new FileWriter(f);
                fw.write("url=jdbc:mysql://127.0.0.1:3306/phone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true\n");
                fw.write("user=root\n");
                fw.write("password=\n");
                fw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MethodesBDDImpl.checkTable();


        int counter = 0;
        //ajoute 450 sim aléatoirement
        for (int i = 0; i < 450; i++) {
            int num = (int) (Math.random() * 1000000);
            int sim = (int) (Math.random() * 1000000);
            if (MethodesBDDImpl.addSim(sim, num)) {
                counter++;
                continue;
            } else {
                for (int j = 0; j < 10; j++) {
                    num = (int) (Math.random() * 1000000);
                    sim = (int) (Math.random() * 1000000);
                    if (MethodesBDDImpl.addSim(sim, num)) {
                        counter++;
                        break;
                    }
                }
            }
        }
        System.out.println("Sim ajouté: " + counter);


    }

    //get bdd
    public static MethodesBDD getBDD() {
        return new MethodesBDD();
    }
}
