package com.devpatil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;
import spark.Spark;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AuthController {
    private static final String URL = "jdbc:mysql://localhost:3306/ai_music_auth";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dev@1343";

    public static String signUp(Request req, Response res) {
        // Parse JSON body
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.body(), JsonObject.class);
        
        String username = json.has("username") ? json.get("username").getAsString() : null;
        String email = json.has("email") ? json.get("email").getAsString() : null;
        String password = json.has("password") ? json.get("password").getAsString() : null;

        // Validate inputs
        if (username == null || username.isEmpty()) {
            return "{\"error\": \"Username cannot be null or empty\"}";
        }
        if (email == null || email.isEmpty()) {
            return "{\"error\": \"Email cannot be null or empty\"}";
        }
        if (password == null || password.isEmpty()) {
            return "{\"error\": \"Password cannot be null or empty\"}";
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
            PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, passwordHash);
            int rows = stmt.executeUpdate();
            return rows > 0 ? "{\"status\": \"User created\"}" : "{\"error\": \"Failed to create user\"}";
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\": \"Database error\"}";
        }
    }

    public static String login(Request req, Response res) {
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(req.body(), JsonObject.class);
        
        String email = json.has("email") ? json.get("email").getAsString() : null;
        String password = json.has("password") ? json.get("password").getAsString() : null;

        if (email == null || email.isEmpty()) {
            return "{\"error\": \"Email cannot be null or empty\"}";
        }
        if (password == null || password.isEmpty()) {
            return "{\"error\": \"Password cannot be null or empty\"}";
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (BCrypt.checkpw(password, storedHash)) {
                    return "{\"status\": \"Login successful\"}";
                } else {
                    return "{\"error\": \"Invalid credentials\"}";
                }
            } else {
                return "{\"error\": \"User not found\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "{\"error\": \"Database error\"}";
        }
    }
    
    public static void checkAuth(Request req, Response res) {
        // Example authentication middleware
        String authToken = req.headers("Authorization");
        if (authToken == null || !isValidToken(authToken)) {
            Spark.halt(401, "Unauthorized");
        }
    }

    private static boolean isValidToken(String token) {
        // Implement token validation logic here
        return true; // Placeholder for token validation
    }
}
