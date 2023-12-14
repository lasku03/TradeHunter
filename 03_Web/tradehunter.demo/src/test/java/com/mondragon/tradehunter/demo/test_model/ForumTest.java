package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
}
