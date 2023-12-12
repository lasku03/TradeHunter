package com.mondragon.tradehunter.demo.request_models;

import lombok.Data;

@Data
public class Request_user {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private int age;
    private boolean premium;
}
