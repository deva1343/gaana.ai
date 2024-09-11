package com.devpatil;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DatabaseTest {

    @BeforeClass
    public void setUp() {
        // Initialize or reset the database for testing
        Database.init();
    }

    @Test
    public void testDatabaseConnection() {
        try (Connection connection = Database.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            fail("Database connection failed");
        }
    }

    @Test
    public void testUserTable() {
        try (Connection connection = Database.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            rs.next();
            int userCount = rs.getInt(1);
            assertTrue(userCount >= 0); // Check if the table exists and has a count of rows
        } catch (SQLException e) {
            fail("Database query failed");
        }
    }
}
