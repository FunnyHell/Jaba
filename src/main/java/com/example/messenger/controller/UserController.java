package com.example.messenger.controller;

import com.example.messenger.dto.UserResponseDTO;


import com.example.messenger.model.UserDetail;
import com.example.messenger.security.JwtToken;

import com.example.messenger.service.UserDetailService;
import com.example.messenger.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal UserDetail userDetail) {
        UserResponseDTO userResponseDTO = UserMapper.userToUserResponseDTO(userDetail);
        return ResponseEntity.ok(userResponseDTO);
    }
}
