package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {
    private static Connection connexion = null;

    public static Connection getConnexion() {
        if (connexion == null) {
            try {
                connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/app?useSSL=false", "root", "");
            } catch (SQLException throwables) {
                System.err.println("Erreur connexion Base de Données !");
            }
        }
        return connexion;
    }
}
