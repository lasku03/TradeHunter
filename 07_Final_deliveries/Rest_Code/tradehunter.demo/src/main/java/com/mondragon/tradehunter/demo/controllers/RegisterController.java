package com.mondragon.tradehunter.demo.controllers;

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
    private UserService userService;

    public RegisterController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = { "application/xml", "application/json" }, consumes = {
            "application/xml", "application/json" })
    public ResponseEntity<String> register(@RequestBody RequestUser requestUser) {
        ResponseEntity<String> responseEntity;
        if (userService.verifyUsername(requestUser.getUsername())
                && (userService.verifyEmail(requestUser.getEmail()))) {
            User user = new User();
            user.setName(requestUser.getName());
            user.setSurname(requestUser.getSurname());
            user.setUsername(requestUser.getUsername());
            user.setPassword(requestUser.getPassword());
            user.setEmail(requestUser.getEmail());
            user.setAge(requestUser.getAge());
            user.setPremium(requestUser.isPremium());
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
