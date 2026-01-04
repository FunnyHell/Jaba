/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.controller;

import com.example.messenger.dto.JwtResponseDto;
import com.example.messenger.dto.UserLoginRequestDto;
import com.example.messenger.model.UserDetail;
import com.example.messenger.security.JwtToken;
import com.example.messenger.service.UserDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JwtResponseDto> userRegister(
            @Valid @RequestPart("data") UserRegisterRequestDto userRegisterRequestDto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        UserResponseDTO result = authService.register(userRegisterRequestDto, file);
        UserDetail userDetail = userDetailService.loadUserByUsername(result.getUsername());
        return new ResponseEntity<>(jwtToken.generateToken(userDetail),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getUsername(),
                            userLoginRequestDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetail userDetail = userDetailService.loadUserByUsername(userLoginRequestDto.getUsername());
            return new ResponseEntity<>(jwtToken.generateToken(userDetail),HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
