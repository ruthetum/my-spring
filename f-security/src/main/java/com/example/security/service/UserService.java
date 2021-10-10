package com.example.security.service;

import com.example.security.entity.User;
import com.example.security.exception.UserErrorCode;
import com.example.security.exception.UserException;
import com.example.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.security.exception.UserErrorCode.DUPLICATED_USER;
import static com.example.security.type.Authority.ROLE_ADMIN;
import static com.example.security.type.Authority.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 등록
    public User signup(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new UserException(DUPLICATED_USER);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authority(ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    // 관리자 등록
    public User signupAdmin(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            throw new UserException(DUPLICATED_USER);
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .authority(ROLE_ADMIN)
                .build();

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
