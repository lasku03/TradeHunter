package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.controllers.RegisterController;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestUser;
import com.mondragon.tradehunter.demo.services.UserService;

class RegisterControllerTest extends EasyMockSupport{
    public UserService userService;
    public User user;
    public RequestUser requestUser;
    public RegisterController registerController;

    @BeforeEach
    void setUp(){
        userService = createMock(UserService.class);
        requestUser = new RequestUser("Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false);
        user = new User(1,"Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false, null, null, null, null);
        registerController = new RegisterController(userService);
    }

    @Test
    void testRegister(){
        EasyMock.expect(userService.verifyUsername("username")).andReturn(true);
        EasyMock.expect(userService.verifyEmail("name.surname@gmail.com")).andReturn(true);
        EasyMock.expect(userService.saveUser(EasyMock.anyObject(User.class))).andReturn(user);
        EasyMock.replay(userService);

        ResponseEntity<String> responseEntity = registerController.register(requestUser);
        assertEquals("Created!", responseEntity.getBody());
        EasyMock.verify(userService);
    }

    @Test
    void testRegisterUsernameExists(){
        EasyMock.expect(userService.verifyUsername("username")).andReturn(false);
        EasyMock.expect(userService.verifyUsername("username")).andReturn(false);
        EasyMock.replay(userService);

        ResponseEntity<String> responseEntity = registerController.register(requestUser);
        assertEquals("Username exists!", responseEntity.getBody());
        EasyMock.verify(userService);
    }

    @Test
    void testRegisterEmailExists(){
        EasyMock.expect(userService.verifyUsername("username")).andReturn(true);
        EasyMock.expect(userService.verifyEmail("name.surname@gmail.com")).andReturn(false);
        EasyMock.expect(userService.verifyUsername("username")).andReturn(true);
        EasyMock.replay(userService);

        ResponseEntity<String> responseEntity = registerController.register(requestUser);
        assertEquals("Email exists!", responseEntity.getBody());
        EasyMock.verify(userService);
    }

}
