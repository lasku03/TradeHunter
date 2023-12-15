package com.mondragon.tradehunter.demo.test_model;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.model.Users;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersTest {

    @Test
    void testGetUsers() {
        // Arrange
        Users users = new Users();
        List<User> userList = new ArrayList<>();

        // Crear algunos usuarios de prueba
        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword("pass2");

        userList.add(user1);
        userList.add(user2);

        // Establecer la lista de usuarios en Users
        users.setUsers(userList);

        // Act
        List<User> retrievedUsers = users.getUsers();

        // Assert
        assertEquals(2, retrievedUsers.size());
        assertEquals("user1", retrievedUsers.get(0).getUsername());
        assertEquals("pass1", retrievedUsers.get(0).getPassword());
        assertEquals("user2", retrievedUsers.get(1).getUsername());
        assertEquals("pass2", retrievedUsers.get(1).getPassword());
    }
}
