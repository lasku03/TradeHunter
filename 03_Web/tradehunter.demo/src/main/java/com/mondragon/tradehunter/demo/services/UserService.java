package com.mondragon.tradehunter.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mondragon.tradehunter.demo.model.User;
import com.mondragon.tradehunter.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> getUserByID(int id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public User login(String username, String password){
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    public boolean verifyUsername(String username){
        boolean verified = false;
        if(userRepository.findUserByUsername(username) == null){
            verified = true;
        }
        return verified;
    }

    public boolean verifyEmail(String email){
        boolean verified = false;
        if(userRepository.findUserByEmail(email) == null){
            verified = true;
        }
        return verified;
    }
}
