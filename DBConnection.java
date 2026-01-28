package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/student_reg_system";
    private static final String USER = "root";  // using default root
    private static final String PASSWORD = "1234";  // change this

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");

        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: JDBC Driver not found!");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("ERROR: Could not connect to DB!");
            System.out.println("Check URL / Username / Password / MySQL server.");
            e.printStackTrace();
        }

        return conn;
    }

    // Test the connection
    public static void main(String[] args) {
        getConnection();
    }
}
