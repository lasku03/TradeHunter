package com.mondragon.tradehunter.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestUser;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
//@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping(value = "/edit", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<User> editUser(@RequestBody RequestUser requestUser){ 
        ResponseEntity<User> responseEntity;
        User user = userService.getUserByUsername(requestUser.getUsername());
        if(user != null){
            user.setName(requestUser.getName());
            user.setSurname(requestUser.getSurname());
            user.setPassword(requestUser.getPassword());
            user.setAge(requestUser.getAge());
            user.setPremium(requestUser.isPremium());
            userService.saveUser(user);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    } 
}
