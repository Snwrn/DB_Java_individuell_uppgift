package se.deved.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionHelper {
    static String connectionString = "jdbc:postgresql://localhost/transaction_project?user=postgres&password=password";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionString);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database", e);
        }
    }
}
