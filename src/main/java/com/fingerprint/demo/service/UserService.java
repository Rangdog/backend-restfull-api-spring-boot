package com.fingerprint.demo.service;

import com.fingerprint.demo.model.User;
import com.fingerprint.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User registerUser(User user){
        return userRepository.save(user);
    }
    public Optional<User> findByUsername(String username){
        return userRepository.findUserByUsername(username);
    }
    public Optional<User> findById(Long userId){
        return userRepository.findById(userId);
    }
}