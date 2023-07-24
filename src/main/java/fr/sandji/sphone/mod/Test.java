
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod;

import com.google.gson.Gson;
import fr.sandji.sphone.mod.server.database.DatabaseManager;
import fr.sandji.sphone.mod.common.phone.Contact;
import net.minecraft.command.CommandWeather;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
    public static void Test() {
        String simcode = "1234";
        try {



            final Connection connection = DatabaseManager.SPHONE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT contacts FROM simcards WHERE sim_code = ?");
            preparedStatement.setString(1, simcode);
            preparedStatement.executeQuery();
            final ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                Gson gson = new Gson();
                Contact[] contacts = gson.fromJson(resultSet.getString("contacts"), Contact[].class);
                for (Contact contact : contacts) {
                    System.out.println("Nom : " + contact.getName());
                }
            } else {
                System.out.println("Erreur : Donnée de la Carte Sim introuvable");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
