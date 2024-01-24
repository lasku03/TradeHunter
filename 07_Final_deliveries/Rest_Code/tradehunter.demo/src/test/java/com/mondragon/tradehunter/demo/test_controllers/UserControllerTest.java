package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.controllers.UserController;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestUser;
import com.mondragon.tradehunter.demo.services.UserService;

class UserControllerTest extends EasyMockSupport {
    public UserService userService;
    public RequestUser requestUser;
    public User user;
    public UserController userController;

    @BeforeEach
    void SetUp() {
        userService = createMock(UserService.class);
        requestUser = new RequestUser("Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false);
        user = new User(1, "Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false, null, null,
                null, null);
        userController = new UserController(userService);
    }

    @Test
    void testEditUser() {
        EasyMock.expect(userService.getUserByUsername("username")).andReturn(user);
        EasyMock.expect(userService.saveUser(user)).andReturn(user);
        EasyMock.replay(userService);

        ResponseEntity<User> responseEntity = userController.editUser(requestUser);
        assertEquals(user, responseEntity.getBody());
        EasyMock.verify(userService);
    }

    @Test
    void testEditUserNull() {
        EasyMock.expect(userService.getUserByUsername("username")).andReturn(null);
        EasyMock.replay(userService);

        ResponseEntity<User> responseEntity = userController.editUser(requestUser);
        assertNull(responseEntity.getBody());
        EasyMock.verify(userService);
    }

    @Test
    void testDeleteUser() {
        EasyMock.expect(userService.getUserByUsername("username")).andReturn(user);
        userService.deleteUser(user);
        EasyMock.replay(userService);

        ResponseEntity<RequestUser> responseEntity = userController.deleteUser("username");

        EasyMock.verify(userService);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
