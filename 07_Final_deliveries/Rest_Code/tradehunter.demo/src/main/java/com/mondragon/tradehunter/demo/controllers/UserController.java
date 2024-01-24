package com.mondragon.tradehunter.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.request_models.RequestUser;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
// @RequestMapping("")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(value = "/edit", produces = { "application/json", "application/xml" }, consumes = { "application/json",
            "application/xml" })
    public ResponseEntity<User> editUser(@RequestBody RequestUser requestUser) {
        ResponseEntity<User> responseEntity;
        User user = userService.getUserByUsername(requestUser.getUsername());
        if (user != null) {
            user.setName(requestUser.getName());
            user.setSurname(requestUser.getSurname());
            user.setPassword(requestUser.getPassword());
            user.setAge(requestUser.getAge());
            user.setPremium(requestUser.isPremium());
            userService.saveUser(user);
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping(value = "/delete/{username}", produces = { "application/json", "application/xml" })
    public ResponseEntity<RequestUser> deleteUser(@PathVariable String username){
        ResponseEntity<RequestUser> responseEntity;
        User user = userService.getUserByUsername(username);
        if(user != null){
            userService.deleteUser(user);
            RequestUser requestUser = new RequestUser(user.getName(), user.getSurname(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAge(), user.isPremium());
            responseEntity = new ResponseEntity<>(requestUser, HttpStatus.OK);
        }else{
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }
}
