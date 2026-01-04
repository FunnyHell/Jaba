/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.service;

import com.example.messenger.model.UserProfile;
import com.example.messenger.utils.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.User;
import com.example.messenger.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author FunnyHell
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;

    @Transactional
    public UserResponseDTO register(UserRegisterRequestDto userRegisterDto, MultipartFile file) {

        String avatarPath = fileStorageService.saveFile(file);

        UserProfile userProfile = userMapper.UserRegisterDTOToUserProfile(userRegisterDto);
        userProfile.setProfilePic(avatarPath);

        String hashedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

        User user = User.builder()
                        .email(userRegisterDto.getEmail())
                        .username(userRegisterDto.getUsername())
                        .password(hashedPassword)
                        .build();
        user.setProfile(userProfile);

        User saved = userRepository.save(user);

        return UserResponseDTO.builder()
                .userId(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .build();
    }

}
