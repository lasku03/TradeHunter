package com.mondragon.tradehunter.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.repository.SearchRepository;
import com.mondragon.tradehunter.demo.repository.UserRepository;

@Service
public class SearchService {
    @Autowired
    SearchRepository searchRepository;

    @Autowired
    UserRepository userRepository;
}
