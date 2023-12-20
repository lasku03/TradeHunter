package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.controllers.LoginController;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.services.UserService;

class LoginControllerTest extends EasyMockSupport{

    public UserService userService;
    public User user;
    public LoginController loginController;

    @BeforeEach
    void SetUp(){
        userService = createMock(UserService.class);
        user = new User(1, "Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false, null, null, null, null);
        loginController = new LoginController(userService);
    }

    @Test
    void testLogin() {
        EasyMock.expect(userService.login("username", "password")).andReturn(user);
        EasyMock.replay(userService);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "username");
        loginRequest.put("password", "password");

        ResponseEntity<User> responseEntity = loginController.login(loginRequest);
        assertEquals(user, responseEntity.getBody());
        EasyMock.verify(userService);
    }

    @Test
    void testLoginNull(){
        EasyMock.expect(userService.login("new_username", "new_password")).andReturn(null);
        EasyMock.replay(userService);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "new_username");
        loginRequest.put("password", "new_password");

        ResponseEntity<User> responseEntity = loginController.login(loginRequest);
        assertNull(responseEntity.getBody());
        EasyMock.verify(userService);
    }
}
