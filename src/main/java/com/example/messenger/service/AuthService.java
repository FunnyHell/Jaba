/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.service;

import com.example.messenger.model.User;
import org.springframework.stereotype.Service;

import com.example.messenger.dto.UserRegisterDto;
import com.example.messenger.dto.UserResponceDTO;
import com.example.messenger.repository.UserRepository;

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

    public UserResponceDTO register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        User user = User.builder()
                        .email(userRegisterDto.getEmail())
                        .username(userRegisterDto.getUsername())
                        .password(userRegisterDto.getPassword())
                        .build();        

        User saved = userRepository.save(user);
        return new UserResponceDTO("User registered successfully", saved.getId(), saved.getUsername(), saved.getEmail());
    }

}
