package com.mondragon.tradehunter.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.repository.PredictionRepository;
import com.mondragon.tradehunter.demo.repository.UserRepository;

@Service
public class PredictionService {
    @Autowired
    PredictionRepository predictionRepository;

    @Autowired
    UserRepository userRepository;
}
