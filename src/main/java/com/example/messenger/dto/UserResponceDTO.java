/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.example.messenger.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author FunnyHell
 */
@Getter
@Setter
public class UserResponceDTO {
    private String message;
    private Long userId;
    private String username;
    private String email;

    public UserResponceDTO(String message, Long userId, String username, String email) {
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}
