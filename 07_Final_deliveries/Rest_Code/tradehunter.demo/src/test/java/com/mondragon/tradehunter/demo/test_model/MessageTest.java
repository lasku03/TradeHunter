package com.mondragon.tradehunter.demo.test_model;

import static org.junit.Assert.assertEquals;

import java.util.Date;

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
        Date now = new Date();
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

    @Test
    void testConstructor(){
        User user = new User();
        Forum forum = new Forum();
        Date date = new Date();
        Message message = new Message(1, "Message content",date, user, forum);
        assertEquals(1, message.getMessageID());
        assertEquals("Message content", message.getContent());
        assertEquals(date, message.getDate());
        assertEquals(user, message.getUser());
        assertEquals(forum, message.getForum());
    }
}
