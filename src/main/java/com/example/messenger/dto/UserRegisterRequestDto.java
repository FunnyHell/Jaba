package com.example.messenger.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 *
 * @author FunnyHell
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password is mandatory")
    private String password;

//    Profile
    @NotBlank(message = "First name is mandatory")
    private String firstName;

    private String lastName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;

    @Past
    private LocalDate birthDate;

    private String profilePic;

    @Size(max = 500, message = "Bio must be no more than 500 characters")
    private String bio;

    private String city;
    private String country;

    private Boolean gender;
}
