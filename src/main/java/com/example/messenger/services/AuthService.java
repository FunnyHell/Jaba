/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.services;

import org.springframework.stereotype.Service;

import com.example.messenger.models.User;
import com.example.messenger.repositories.UserRepository;

/**
 *
 * @author FunnyHell
 */
@Service
public class AuthService {
    private final UserRepository userRepository;
    
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public static String registerUser() {
        return "User registered successfully!";
    }

}
