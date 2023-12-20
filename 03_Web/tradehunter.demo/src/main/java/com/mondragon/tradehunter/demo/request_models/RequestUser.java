package com.mondragon.tradehunter.demo.request_models;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RequestUser {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private int age;
    private boolean premium;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public RequestUser(String name, String surname, String username, String password, String email, int age,
            boolean premium) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.premium = premium;
    }
}
