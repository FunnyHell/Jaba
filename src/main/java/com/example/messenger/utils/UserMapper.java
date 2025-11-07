package com.example.messenger.utils;

import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.UserDetail;

public class UserMapper {
    public static UserResponseDTO userToUserResponseDTO(UserDetail user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail());
    }
}
