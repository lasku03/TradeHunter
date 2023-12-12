package com.mondragon.tradehunter.demo.test_request_models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.User;

public class Request_userTest {
    @Test
    void testNameGetterAndSetter() {
        User user = new User();
        user.setName("Name");
        assertEquals("Name", user.getName());
    }

    @Test
    void testSurnameGetterAndSetter() {
        User user = new User();
        user.setSurname("Surname");
        assertEquals("Surname", user.getSurname());
    }

    @Test
    void testUsernameGetterAndSetter() {
        User user = new User();
        user.setUsername("name.surname");
        assertEquals("name.surname", user.getUsername());
    }

    @Test
    void testPasswordGetterAndSetter() {
        User user = new User();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testEmailGetterAndSetter() {
        User user = new User();
        user.setEmail("name.surname@email.com");
        assertEquals("name.surname@email.com", user.getEmail());
    }

    @Test
    void testAgeGetterAndSetter() {
        User user = new User();
        user.setAge(25);
        assertEquals(25, user.getAge());
    }

    @Test
    void testPremiumGetterAndSetter() {
        User user = new User();
        user.setPremium(true);
        assertTrue(user.isPremium());
    }
}
