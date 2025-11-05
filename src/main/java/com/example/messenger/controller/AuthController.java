/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.controller;

import com.example.messenger.dto.JwtResponseDto;
import com.example.messenger.model.UserDetail;
import com.example.messenger.security.JwtToken;
import com.example.messenger.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


/**
 *
 * @author FunnyHell
 */
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final UserDetailService userDetailService;
    private final JwtToken jwtToken;

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDto> userRegister(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        UserResponseDTO result = authService.register(userRegisterRequestDto);
        UserDetail userDetail = userDetailService.loadUserByUsername(result.getUsername());
        return new ResponseEntity<>(jwtToken.generateToken(userDetail),HttpStatus.OK);
    }

}
