package com.mondragon.tradehunter.demo.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.request_models.RequestMessage;
import com.mondragon.tradehunter.demo.services.ForumService;
import com.mondragon.tradehunter.demo.services.MessageService;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
// @RequestMapping("")
public class ForumControler {
    ForumService forumService;
    MessageService messageService;
    UserService userService;

    public ForumControler(ForumService forumService, MessageService messageService, UserService userService){
        this.forumService = forumService;
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping(value = "/forum/{forumID}", produces = { "application/json",
            "application/xml" })
    public ResponseEntity<List<RequestMessage>> getMessages(@PathVariable int forumID) {
        ResponseEntity<List<RequestMessage>> responseEntity;

        Optional<Forum> forum = forumService.getForumByID(forumID);
        if (forum.isPresent()) {
            List<Message> messages = messageService.getMessagesByForum(forum);
            List<RequestMessage> requestMessages = new ArrayList<>();
            for (Message message : messages) {
                RequestMessage requestMessage = new RequestMessage(message.getContent(),
                        message.getUser().getUsername(), message.getForum().getForumID(), message.getDate());
                requestMessages.add(requestMessage);
            }
            responseEntity = new ResponseEntity<>(requestMessages, HttpStatus.OK);
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }

        return responseEntity;
    }

    @GetMapping(value = "/forum")
    public ResponseEntity<String> putMessage() {
        ResponseEntity<String> responseEntity;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RequestMessage.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            File xmlFile = new File("\\home\\unai_laskurain\\web\\XMLschemas\\requestMessage.xml");
            RequestMessage requestMessage = (RequestMessage) jaxbUnmarshaller.unmarshal(xmlFile);
            Message message = new Message();
            message.setContent(requestMessage.getContent());
            message.setDate(requestMessage.getDate());
            message.setForum(forumService.getForumByID(requestMessage.getForumID()).orElse(null));
            message.setUser(userService.getUserByUsername(requestMessage.getUserUsername()));
            messageService.saveMessage(message);
            responseEntity = new ResponseEntity<>("Message created successfully", HttpStatus.CREATED);
        } catch (JAXBException e) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }
}