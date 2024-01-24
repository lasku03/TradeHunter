package com.mondragon.tradehunter.demo.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.model.Forum;
import com.mondragon.tradehunter.demo.repository.ForumRepository;
import com.mondragon.tradehunter.demo.repository.MessageRepository;
import com.mondragon.tradehunter.demo.repository.UserRepository;

@Service
public class ForumService {
    ForumRepository forumRepository;
    public ForumService(ForumRepository forumRepository){
        this.forumRepository = forumRepository;
    }

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    public Optional<Forum> getForumByID(int forumID){
        return forumRepository.findById(forumID);
    }

}
