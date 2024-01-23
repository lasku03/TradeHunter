package com.mondragon.tradehunter.demo.test_controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mondragon.tradehunter.demo.controllers.ForumControler;
import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestMessage;
import com.mondragon.tradehunter.demo.services.ForumService;
import com.mondragon.tradehunter.demo.services.MessageService;
import com.mondragon.tradehunter.demo.services.UserService;

class ForumControllerTest extends EasyMockSupport {

    public ForumService forumService;
    public MessageService messageService;
    public ForumControler forumController;
    public UserService userService;

    @BeforeEach
    void setUp() {
        forumService = createMock(ForumService.class);
        messageService = createMock(MessageService.class);
        userService = createMock(UserService.class);
        forumController = new ForumControler(forumService, messageService, userService);
    }

    @Test
    void testGetMessagesForumExistsWithMessages() {
        int forumID = 1;
        Message message = new Message();
        User user = new User();
        user.setUsername("username");
        message.setUser(user);
        List<Message> messages = Arrays.asList(message);
        Optional<Forum> forum = Optional.of(new Forum(forumID, "Description", messages, user, Arrays.asList(user)));
        messages.get(0).setForum(forum.get());

        EasyMock.expect(forumService.getForumByID(forumID)).andReturn(forum);
        EasyMock.expect(messageService.getMessagesByForum(forum)).andReturn(messages);
        EasyMock.replay(forumService, messageService);

        ResponseEntity<List<RequestMessage>> responseEntity = forumController.getMessages(forumID);
        assertNotNull(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(messages.size(), responseEntity.getBody().size());
        EasyMock.verify(forumService, messageService);
    }

    @Test
    void testGetMessagesForumDoesNotExists() {
        int forumID = 3;

        EasyMock.expect(forumService.getForumByID(forumID)).andReturn(Optional.empty());
        EasyMock.replay(forumService);

        ResponseEntity<List<RequestMessage>> responseEntity = forumController.getMessages(forumID);
        assertNull(responseEntity.getBody());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        EasyMock.verify(forumService);
    }

    @Test
    void testPutMessageSuccess() {
        // Mock the behavior of the forumService and userService
        EasyMock.expect(forumService.getForumByID(EasyMock.anyInt())).andReturn(Optional.of(new Forum()));
        EasyMock.expect(userService.getUserByUsername(EasyMock.anyString())).andReturn(new User());

        // Simulate the call to the void method saveMessage
        EasyMock.expect(messageService.saveMessage(EasyMock.anyObject(Message.class))).andReturn(new Message());
        EasyMock.expectLastCall();

        // Replay the mocked objects
        EasyMock.replay(messageService, forumService, userService);

        // Call the method to test
        ResponseEntity<String> response = forumController.putMessage();

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verify that the mocked methods were called
        EasyMock.verify(messageService, forumService, userService);
    }
}
