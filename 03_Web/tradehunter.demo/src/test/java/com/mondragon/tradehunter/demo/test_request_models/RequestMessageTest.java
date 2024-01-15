package com.mondragon.tradehunter.demo.test_request_models;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.request_models.RequestMessage;

class RequestMessageTest {
    @Test
    void testContentGetterAndSetter() {
        RequestMessage message = new RequestMessage();
        message.setContent("Test Content");
        assertEquals("Test Content", message.getContent());
    }

    @Test
    void testDateGetterAndSetter() {
        RequestMessage message = new RequestMessage();
        LocalDateTime now = LocalDateTime.now();
        message.setDate(now);
        assertEquals(now, message.getDate());
    }

    @Test
    void testUserUsernameGetterAndSetter(){
        RequestMessage message = new RequestMessage();
        message.setUserUsername("User");
        assertEquals("User", message.getUserUsername());
    }

    @Test
    void testForumIDGetterAndSetter(){
        RequestMessage message = new RequestMessage();
        message.setForumID(1);
        assertEquals(1, message.getForumID());
    }

    @Test
    void testConstructor(){
        LocalDateTime date = LocalDateTime.now();
        RequestMessage message = new RequestMessage("Message content", "User", 1, date);
        assertEquals("Message content", message.getContent());
        assertEquals(date, message.getDate());
        assertEquals("User", message.getUserUsername());
        assertEquals(1, message.getForumID());
    }
}
