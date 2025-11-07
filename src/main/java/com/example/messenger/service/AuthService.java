/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.User;
import com.example.messenger.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author FunnyHell
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO register(UserRegisterRequestDto userRegisterDto) {

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        User user = User.builder()
                        .email(userRegisterDto.getEmail())
                        .username(userRegisterDto.getUsername())
                        .password(hashedPassword)
                        .build();        

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .userId(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .build();
    }

}
