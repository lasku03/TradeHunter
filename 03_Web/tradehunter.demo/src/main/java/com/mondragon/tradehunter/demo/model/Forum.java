package com.mondragon.tradehunter.demo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Forum")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int forumID;

    private String description;

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    List<Message> messages;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userID")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "User_forums", joinColumns = @JoinColumn(name = "forumID"), inverseJoinColumns = @JoinColumn(name = "userID"))
    List<User> users;
}
