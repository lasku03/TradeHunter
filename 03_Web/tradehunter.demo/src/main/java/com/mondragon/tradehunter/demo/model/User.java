package com.mondragon.tradehunter.demo.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "User")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @XmlElement
    private String name;
    @XmlElement
    private String surname;
    @XmlElement
    private String username;
    @XmlElement
    private String password;
    // private String photo;
    @XmlElement
    private String email;
    @XmlElement
    private int age;
    @XmlElement
    private boolean premium;

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Message> messages;

    @XmlElementWrapper(name = "forums")
    @XmlElement(name = "forum")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Forum> forums;

    @XmlElementWrapper(name = "searches")
    @XmlElement(name = "search")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Search> searches;

    @XmlElementWrapper(name = "predictions")
    @XmlElement(name = "prediction")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Prediction> predictions;
}
