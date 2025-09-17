/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.messenger.dto.UserRegisterDto;
import com.example.messenger.dto.UserResponceDTO;
import com.example.messenger.service.AuthService;


/**
 *
 * @author FunnyHell
 */
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponceDTO postMethodName(@RequestBody UserRegisterDto userRegisterDto) {
        return authService.register(userRegisterDto);
    }
    
    
}
