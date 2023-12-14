package com.mondragon.tradehunter.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class Users {
    List<User> users;
    
    @XmlElement(name = "user")
    public List<User> getUsers(){
        return users;
    }
}
