package com.example.messenger.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Builder
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private User user;

    private String firstName;
    private String lastName;

    private String phoneNumber;

    private LocalDate birthDate;

    private String profilePic;

    @Column(length = 500)
    private String bio;

    private String city;
    private String country;

    private Boolean gender;
    private Boolean isOnline;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.gender == null) {
            this.gender = true;
        }

        this.isOnline = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
