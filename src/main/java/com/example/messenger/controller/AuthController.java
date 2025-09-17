/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messenger.dto.UserRegisterDto;
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


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> postMethodName(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserResponseDTO result = authService.register(userRegisterDto);
        return ResponseEntity.ok(result);
    }
    
    
}
