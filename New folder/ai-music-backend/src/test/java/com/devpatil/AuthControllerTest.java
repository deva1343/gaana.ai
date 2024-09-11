package com.devpatil;

import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AuthControllerTest {

    private static final String BASE_URL = "http://localhost:8080";

    private CloseableHttpClient httpClient;

    @BeforeClass
    public void setUp() {
        // Initialize or reset the database
        Database.init();
        // Initialize HTTP client
        httpClient = HttpClients.createDefault();
    }

    @Test
    public void testSignUp() throws Exception {
        HttpPost post = new HttpPost(BASE_URL + "/signup");
        post.setEntity(new StringEntity("username=user1&email=user1@example.com&password=password123"));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        HttpResponse response = httpClient.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        assertTrue(responseBody.contains("User created"));
    }

    @Test
    public void testLogin() throws Exception {
        // First, sign up
        testSignUp();

        HttpPost post = new HttpPost(BASE_URL + "/login");
        post.setEntity(new StringEntity("email=user1@example.com&password=password123"));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        HttpResponse response = httpClient.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        assertTrue(responseBody.contains("Login successful"));
    }

    @Test
    public void testLoginWithInvalidPassword() throws Exception {
        // First, sign up
        testSignUp();

        HttpPost post = new HttpPost(BASE_URL + "/login");
        post.setEntity(new StringEntity("email=user1@example.com&password=wrongpassword"));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        HttpResponse response = httpClient.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        assertTrue(responseBody.contains("Invalid credentials"));
    }

    @Test
    public void testLoginWithNonExistentUser() throws Exception {
        HttpPost post = new HttpPost(BASE_URL + "/login");
        post.setEntity(new StringEntity("email=nonexistent@example.com&password=password123"));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        HttpResponse response = httpClient.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        assertTrue(responseBody.contains("User not found"));
    }
}
