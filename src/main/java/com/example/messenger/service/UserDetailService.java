/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.service;
import com.example.messenger.model.UserProfile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.messenger.model.User;
import com.example.messenger.model.UserDetail;
import com.example.messenger.repository.UserRepository;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author FunnyHell
 */

@Service
@Data
public class UserDetailService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        UserProfile profile = user.getProfile();
        return UserDetail.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneNumber(profile.getPhoneNumber())
                .birthDate(profile.getBirthDate())
                .profilePic(profile.getProfilePic())
                .bio(profile.getBio())
                .city(profile.getCity())
                .country(profile.getCountry())
                .gender(profile.getGender())
                .isOnline(profile.getIsOnline())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }
}
