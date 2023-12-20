package com.mondragon.tradehunter.demo.test_services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.repository.UserRepository;
import com.mondragon.tradehunter.demo.services.UserService;

class UserServiceTest extends EasyMockSupport {

    UserService userService;
    UserRepository userRepository;
    User user;

    @BeforeEach
    void setUp() {
        userRepository = createMock(UserRepository.class);
        userService = new UserService(userRepository);
        user = new User(1, "Name", "Surname", "username", "password", "name.surname@gmail.com", 25, false, null, null,
                null, null);
    }

    @Test
    void testGetUserByID() {
        EasyMock.expect(userRepository.findById(1)).andReturn(Optional.of(user));
        replayAll();
        assertEquals(userService.getUserByID(1), Optional.of(user));
        verifyAll();
    }

    @Test
    void testGetAllUsers() {
        List<User> list = new ArrayList<>();
        list.add(user);
        EasyMock.expect(userRepository.findAll()).andReturn((List<User>) list);
    }

    @Test
    void testLogin() {
        EasyMock.expect(userRepository.findUserByUsernameAndPassword("username", "password")).andReturn(user);
        replayAll();
        assertEquals(userService.login("username", "password"), user);
        verifyAll();
    }

    @Test
    void testVerifyUsernameFalse() {
        EasyMock.expect(userRepository.findUserByUsername("username")).andReturn(user);
        replayAll();
        assertFalse(userService.verifyUsername("username"));
        verifyAll();
    }

    @Test
    void testVerifyUsernameTrue() {
        EasyMock.expect(userRepository.findUserByUsername("new_username")).andReturn(null);
        replayAll();
        assertTrue(userService.verifyUsername("new_username"));
        verifyAll();
    }

    @Test
    void testVerifyGmailFalse() {
        EasyMock.expect(userRepository.findUserByEmail("name.surname@gmail.com")).andReturn(user);
        replayAll();
        assertFalse(userService.verifyEmail("name.surname@gmail.com"));
        verifyAll();
    }

    @Test
    void testVerifyGmailTrue() {
        EasyMock.expect(userRepository.findUserByEmail("name.surname@gmail.com")).andReturn(null);
        replayAll();
        assertTrue(userService.verifyEmail("name.surname@gmail.com"));
        verifyAll();
    }
}
