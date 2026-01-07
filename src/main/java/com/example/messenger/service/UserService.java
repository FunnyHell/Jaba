package com.example.messenger.service;

import com.example.messenger.dto.UserResponseDTO;
import com.example.messenger.model.User;
import com.example.messenger.model.UserDetail;
import com.example.messenger.model.UserProfile;
import com.example.messenger.repository.UserRepository;
import com.example.messenger.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponseDTO getUserProfile(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        UserProfile userProfile = user.getProfile();
        return userMapper.userAndProfileToDTO(user, userProfile);

    }
}
