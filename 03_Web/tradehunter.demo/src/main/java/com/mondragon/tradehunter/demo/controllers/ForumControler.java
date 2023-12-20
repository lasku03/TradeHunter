package com.mondragon.tradehunter.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.services.ForumService;
import com.mondragon.tradehunter.demo.services.MessageService;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
//@RequestMapping("")
public class ForumControler {
    @Autowired
    ForumService forumService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;
}
