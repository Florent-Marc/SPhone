
/*
 * SPhone - Tous droits réservés. (by 0hSandji)
 */

package fr.sandji.sphone.mod;

import fr.sandji.sphone.api.database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {

    public static void Test() {
        try {
            final Connection connection = DatabaseManager.SPHONE.getDatabaseAccess().getConnection();
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT contacts FROM simcards WHERE sim_code = 1234");
            preparedStatement.executeQuery();
            final ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                System.out.println(resultSet.getString("contacts"));
            } else {
                System.out.println("Erreur : Donnée de la Carte Sim introuvable");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
