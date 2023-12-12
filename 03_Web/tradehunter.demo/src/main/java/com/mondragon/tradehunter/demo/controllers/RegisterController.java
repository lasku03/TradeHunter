package com.mondragon.tradehunter.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.Request_user;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
//@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/register", produces = {"application/xml", "application/json"}, consumes = {"application/xml", "application/json"})
    public ResponseEntity<User> register(@RequestBody Request_user requestUser){
        ResponseEntity<User> responseEntity;
        if(requestUser != null){
            User user = User.builder()
            .name(requestUser.getName())
            .surname(requestUser.getSurname())
            .username(requestUser.getUsername())
            .password(requestUser.getPassword())
            .email(requestUser.getEmail())
            .age(requestUser.getAge())
            .premium(requestUser.isPremium())
            .build();
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }
}
