package com.mondragon.tradehunter.demo.model;

import java.util.List;

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

    private String name;
    private String surname;
    private String username;
    private String password;
    // private String photo;
    private String email;
    private int age;
    private boolean premium;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Message> messages;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Forum> forums;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Search> searches;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Prediction> predictions;
}
