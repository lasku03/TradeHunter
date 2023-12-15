package com.mondragon.tradehunter.demo.controllers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mondragon.tradehunter.demo.model.Users;
import com.mondragon.tradehunter.demo.services.ForumService;
import com.mondragon.tradehunter.demo.services.MessageService;
import com.mondragon.tradehunter.demo.services.UserService;

@RestController
//@RequestMapping("")
public class ForumControler {
    @Autowired
    ForumService forumService;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @GetMapping("/a")
    public void dfed(){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File(".\\src\\main\\resources\\schemas\\allXML.xml");

            // this will create Java object - country from the XML file
            Users list = (Users) jaxbUnmarshaller.unmarshal(XMLfile);

            System.out.println(list.toString());
        } catch (JAXBException  e) {
        }
    }
}
