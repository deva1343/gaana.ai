package com.devpatil;
import static spark.Spark.before;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
	
    public static void main(String[] args) 
    {
        // Set the port for the web server
    	port(8081);  // Change to any available port
        // Your routes and app logic

        // Initialize the database
        Database.init();

        // Apply CORS filter to all routes
        before("*", CorsFilter.applyCors);

        // Middleware for authentication on specific routes
        before("/music/*", AuthController::checkAuth);

        // Route for user sign-up
        post("/signup", AuthController::signUp);

        // Route for user login
        post("/login", AuthController::login);
    }
}
