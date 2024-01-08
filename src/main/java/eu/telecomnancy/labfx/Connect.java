package eu.telecomnancy.labfx;

import java.io.File;
import java.nio.file.Paths;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public Connect() {
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            String dbPath = Paths.get("src", "main", "resources", "eu", "telecomnancy", "labfx", "database", "data.db").toAbsolutePath().toString();

            // Charger le pilote JDBC SQLite
            Class.forName("org.sqlite.JDBC");

            // Créer une connexion à la base de données
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            // Créer une connexion à la base de données
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            System.out.println("Connexion à la base de données réussie !");
        } catch (Exception e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }

        return connection;
    }
}