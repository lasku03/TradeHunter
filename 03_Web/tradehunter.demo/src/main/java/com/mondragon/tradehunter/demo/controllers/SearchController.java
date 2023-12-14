package com.mondragon.tradehunter.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.services.SearchService;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
@RequestMapping("")
public class SearchController {
    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;
}
