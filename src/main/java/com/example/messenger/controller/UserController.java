package com.example.messenger.controller;

import com.example.messenger.dto.UserResponseDTO;


import com.example.messenger.model.UserDetail;

import com.example.messenger.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal UserDetail userDetail) {
        UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(userDetail);
        return ResponseEntity.ok(userResponseDTO);
    }
}
