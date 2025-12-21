package ma.project.dentalTech.configuration.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionPool {

    private static final String URL = "jdbc:mysql://localhost:3306/dentaltech";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Erreur connexion DB", e);
        }
    }
}
