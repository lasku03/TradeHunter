package com.mondragon.tradehunter.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestUser;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
// @RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/register", produces = { "application/xml", "application/json" }, consumes = {
            "application/xml", "application/json" })
    public ResponseEntity<String> register(@RequestBody RequestUser requestUser) {
        ResponseEntity<String> responseEntity;
        if (userService.verifyUsername(requestUser.getUsername())
                && (userService.verifyEmail(requestUser.getEmail()))) {
            User user = User.builder()
                    .name(requestUser.getName())
                    .surname(requestUser.getSurname())
                    .username(requestUser.getUsername())
                    .password(requestUser.getPassword())
                    .email(requestUser.getEmail())
                    .age(requestUser.getAge())
                    .premium(requestUser.isPremium())
                    .build();
            userService.saveUser(user);
            responseEntity = new ResponseEntity<>("Created!", HttpStatus.CREATED);
        } else if (!userService.verifyUsername(requestUser.getUsername())) {
            responseEntity = new ResponseEntity<>("Username exists!", HttpStatus.NOT_ACCEPTABLE);
        }else{
            responseEntity = new ResponseEntity<>("Email exists!", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }
}
