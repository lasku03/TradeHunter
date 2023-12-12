package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.request_models.RequestUser;

class RegisterControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testRegister(){
        RequestUser requestUser = new RequestUser();
        requestUser.setName("Name");
        requestUser.setSurname("Surname");
        requestUser.setUsername("name.surname");
        requestUser.setPassword("password123");
        requestUser.setEmail("name.surname@example.com");
        requestUser.setAge(25);
        requestUser.setPremium(true);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/register", requestUser, String.class);
        if(responseEntity.getStatusCode() == HttpStatus.OK){
            assertEquals("Created!", responseEntity.getBody());
        }
    }
}
