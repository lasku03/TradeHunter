package com.mondragon.tradehunter.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.repository.MessageRepository;
import com.mondragon.tradehunter.demo.repository.UserRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;
}
