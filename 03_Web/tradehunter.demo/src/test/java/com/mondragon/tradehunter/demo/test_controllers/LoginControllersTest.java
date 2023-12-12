package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.model.User;

class LoginControllersTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testLogin(){
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "username");
        loginRequest.put("password", "password");

        ResponseEntity<User> responseEntity = restTemplate.postForEntity("/login", loginRequest, User.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK){
            User user = responseEntity.getBody();
            assertEquals("username", user.getUsername());
        }
    }
}
