package com.mondragon.tradehunter.demo.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
//@RequestMapping("/dede")
public class LoginController {
    private UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/login", produces = {"applicacion/xml", "application/json"}, consumes = {"application/xml", "application/json"})
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginRequest){
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        User user = userService.login(username, password);
        ResponseEntity<User> responseEntity;
        if(user != null){
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @GetMapping(value = "/password/{email}", produces = { "application/json",
            "application/xml" })
    public ResponseEntity<User> getUser(@PathVariable String email) {
        ResponseEntity<User> responseEntity;

        User user = userService.getUserByEmail(email);

        if(user != null){
            responseEntity = new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;

    }

}
