package com.mondragon.tradehunter.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.model.Message;
import com.mondragon.tradehunter.demo.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getMessagesByForum(Optional<Forum> forum){
        return messageRepository.findByForum(forum);
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }
}
