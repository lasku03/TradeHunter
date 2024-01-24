package com.mondragon.tradehunter.demo.test_request_models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.request_models.RequestUser;

class RequestUserTest {
    @Test
    void testNameGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setName("Name");
        assertEquals("Name", user.getName());
    }

    @Test
    void testSurnameGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setSurname("Surname");
        assertEquals("Surname", user.getSurname());
    }

    @Test
    void testUsernameGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setUsername("name.surname");
        assertEquals("name.surname", user.getUsername());
    }

    @Test
    void testPasswordGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testEmailGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setEmail("name.surname@email.com");
        assertEquals("name.surname@email.com", user.getEmail());
    }

    @Test
    void testAgeGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setAge(25);
        assertEquals(25, user.getAge());
    }

    @Test
    void testPremiumGetterAndSetter() {
        RequestUser user = new RequestUser();
        user.setPremium(true);
        assertTrue(user.isPremium());
    }

    @Test
    void testConstructor(){
        RequestUser user = new RequestUser("Name", "Surname", "username", "password", "name.surname@gmail.com", 25, true);
        assertEquals("Name", user.getName());
        assertEquals("Surname", user.getSurname());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("name.surname@gmail.com", user.getEmail());
        assertEquals(25, user.getAge());
        assertTrue(user.isPremium());
    }
}
