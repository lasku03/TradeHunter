package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.model.User;

class ForumTest {
    @Test
    void testForumIDGetterAndSetter() {
        Forum forum = new Forum();
        forum.setForumID(1);
        assertEquals(1, forum.getForumID());
    }

    @Test
    void testDescriptionGetterAndSetter() {
        Forum forum = new Forum();
        forum.setDescription("Test Forum");
        assertEquals("Test Forum", forum.getDescription());
    }

    @Test
    void testMessagesGetterAndSetter() {
        Forum forum = new Forum();
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        messages.add(message);
        forum.setMessages(messages);
        assertEquals(messages, forum.getMessages());
    }

    @Test
    void testUserGetterAndSetter() {
        Forum forum = new Forum();
        User user = new User();
        forum.setUser(user);
        assertEquals(user, forum.getUser());
    }

    @Test
    void testUsersGetterAndSetter() {
        Forum forum = new Forum();
        List<User> users = new ArrayList<>();
        User forumUser = new User();
        users.add(forumUser);
        forum.setUsers(users);
        assertEquals(users, forum.getUsers());
    }

    @Test
    void testConstructor(){
        User user = new User();
        Message message = new Message();
        List<Message> messages = new ArrayList<>();
        List<User> users = new ArrayList<>();
        users.add(user);
        messages.add(message);
        Forum forum = new Forum(1, "Description of the Forum", messages, user, users);
        assertEquals(1, forum.getForumID());
        assertEquals("Description of the Forum", forum.getDescription());
        assertEquals(messages, forum.getMessages());
        assertEquals(user, forum.getUser());
        assertEquals(user, forum.getUser());
        assertEquals(users, forum.getUsers());
    }
}
