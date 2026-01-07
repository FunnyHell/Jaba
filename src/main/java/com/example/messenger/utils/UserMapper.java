package com.example.messenger.utils;

import com.example.messenger.dto.UserRegisterRequestDto;
import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.User;
import com.example.messenger.model.UserDetail;
import com.example.messenger.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO userDetailToUserResponseDTO(UserDetail user) {
        if (user == null) return null;

        return UserResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }

    public UserResponseDTO userAndProfileToDTO(User user, UserProfile userProfile) {
        if (user == null) return null;

        String profilePic = "/media/avatars/" + userProfile.getProfilePic();
        String address = this.formatAddress(userProfile.getCity(), userProfile.getCountry());
        String gender = this.mapGender(userProfile.getGender());

        return UserResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .phoneNumber(userProfile.getPhoneNumber())
                .birthDate(userProfile.getBirthDate())
                .profilePicture(profilePic)
                .bio(userProfile.getBio())
                .city(userProfile.getCity())
                .country(userProfile.getCountry())
                .address(address)
                .gender(gender)
                .isOnline(userProfile.getIsOnline())
                .createdAt(userProfile.getCreatedAt())
                .updatedAt(userProfile.getUpdatedAt())
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
