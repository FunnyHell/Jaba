package com.example.messenger.utils;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.UserDetail;
import com.example.messenger.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO userToUserResponseDTO(UserDetail user) {
        if (user == null) return null;

        String profilePic = "/media/avatars/" + user.getProfilePic();
        String address = this.formatAddress(user.getCity(), user.getCountry());
        String gender = this.mapGender(user.getGender());

        return UserResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .profilePicture(profilePic)
                .bio(user.getBio())
                .city(user.getCity())
                .country(user.getCountry())
                .address(address)
                .gender(gender)
                .isOnline(user.getIsOnline())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private String formatAddress(String city, String country) {
        if (city == null && country == null) return null;
        if (city == null) return country;
        if (country == null) return city;
        return city + "," + country;
    }

    private String mapGender(Boolean gender) {
        return gender ? "M" : "F";
    }

    public UserProfile UserRegisterDTOToUserProfile(UserRegisterRequestDto userRegisterDto) {
        return UserProfile.builder()
                .firstName(userRegisterDto.getFirstName())
                .lastName(userRegisterDto.getLastName())
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .birthDate(userRegisterDto.getBirthDate())
                .bio(userRegisterDto.getBio())
                .city(userRegisterDto.getCity())
                .country(userRegisterDto.getCountry())
                .gender(userRegisterDto.getGender())
                .build();
    }
}
