package com.mondragon.tradehunter.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mondragon.tradehunter.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByEmail(String email);
}
