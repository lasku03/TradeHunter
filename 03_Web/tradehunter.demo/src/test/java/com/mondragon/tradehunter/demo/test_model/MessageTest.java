package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.model.User;

class MessageTest {
    @Test
    void testMessageIDGetterAndSetter() {
        Message message = new Message();
        message.setMessageID(1);
        assertEquals(1, message.getMessageID());
    }

    @Test
    void testContentGetterAndSetter() {
        Message message = new Message();
        message.setContent("Test Content");
        assertEquals("Test Content", message.getContent());
    }

    @Test
    void testDateGetterAndSetter() {
        Message message = new Message();
        LocalDateTime now = LocalDateTime.now();
        message.setDate(now);
        assertEquals(now, message.getDate());
    }

    @Test
    void testUserGetterAndSetter() {
        Message message = new Message();
        User user = new User();
        message.setUser(user);
        assertEquals(user, message.getUser());
    }

    @Test
    void testForumGetterAndSetter() {
        Message message = new Message();
        Forum forum = new Forum();
        message.setForum(forum);
        assertEquals(forum, message.getForum());
    }
}
