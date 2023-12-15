package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.model.User;

class LoginControllersTest {

    public TestRestTemplate restTemplate;

    public LoginControllersTest() {
        restTemplate = new TestRestTemplate();
    }

    @Test
    void testLogin() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "username");
        loginRequest.put("password", "password");

        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://localhost:8080/login", loginRequest,
                User.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            User user = responseEntity.getBody();
            assertEquals("username", user.getUsername());
        } else {
            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }
    }
}
