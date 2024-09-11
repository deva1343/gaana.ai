package com.devpatil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class Database {
    
    private static final String URL = "jdbc:mysql://localhost:3306/ai_music_auth";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dev@1343";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Initialize or reset the database
    public static void init() {
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            // Drop tables if they exist
            stmt.executeUpdate("DROP TABLE IF EXISTS users");
            // Recreate tables
            stmt.executeUpdate("CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "email VARCHAR(255) NOT NULL UNIQUE, " +
                "password_hash VARCHAR(255) NOT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
/*

Added DEFAULT CHARSET=utf8mb4: Ensures that the table uses the utf8mb4 character set, 
which supports a wide range of Unicode characters.

 Added ENGINE=InnoDB: Specifies the storage engine to use for the table.
InnoDB is a common choice for MySQL and supports transactions and foreign keys.
*/        
        
        
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
