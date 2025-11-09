package org.example.bankingsystem.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    Properties prop = new Properties();
                    if (input == null) {
                        throw new RuntimeException("Unable to find db.properties in resources");
                    }
                    prop.load(input);

                    String url = prop.getProperty("db.url");
                    String username = prop.getProperty("db.username");
                    String password = prop.getProperty("db.password");
                    String driver = prop.getProperty("db.driver");

                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, username, password);
                    System.out.println("✅ Database connected successfully.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get DB connection", e);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
