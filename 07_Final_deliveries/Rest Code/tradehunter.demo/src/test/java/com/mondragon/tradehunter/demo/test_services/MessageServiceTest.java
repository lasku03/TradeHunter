package com.mondragon.tradehunter.demo.test_services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.repository.MessageRepository;
import com.mondragon.tradehunter.demo.services.MessageService;

class MessageServiceTest extends EasyMockSupport{
    MessageRepository messageRepository;
    MessageService messageService;
    Message message;
    Optional<Forum> forum;

    @BeforeEach
    void setUp() {
        messageRepository = createMock(MessageRepository.class);
        messageService = new MessageService(messageRepository);
        Date date = new Date();
        message = new Message(1, "content", date, new User(), new Forum());
    }

    @Test
    void testSaveMessage(){
        EasyMock.expect(messageRepository.save(message)).andReturn(message);
        replayAll();
        assertEquals(messageService.saveMessage(message), message);
        verifyAll();
    }

        @Test
    void testGetMessagesByForum(){
        List<Message> list = new ArrayList<>();
        list.add(message);
        EasyMock.expect(messageRepository.findByForum(forum)).andReturn(list);
        replayAll();
        assertEquals(messageService.getMessagesByForum(forum), list);
    }
}
