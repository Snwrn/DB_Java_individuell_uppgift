package se.deved.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//COnnection for the database is treated from this helper, to clean up Main and make it more logical
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
