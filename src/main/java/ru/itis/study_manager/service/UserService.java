package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.study_manager.data.UserData;
import ru.itis.study_manager.dto.UserDto;

@RequiredArgsConstructor
public class UserService {
    private final UserData userData;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserDto registerUser(String username, String password) {
        Long userId = userData.createUser(username, encoder.encode(password));
        if (userId == null) {
            return null;
        }
        return userData.getUserDto(userId);
    }

    public UserDto authenticateUser(String username, String rawPassword) {
        String passwordHash = userData.getPasswordHash(username);
        if (encoder.matches(rawPassword, passwordHash)) {
            return userData.getUserDto(username);
        }
        return null;
    }
}
