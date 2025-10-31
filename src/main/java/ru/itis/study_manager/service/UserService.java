package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.study_manager.data.UserData;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.util.RegexUtil;

@RequiredArgsConstructor
public class UserService extends Service {
    private final UserData userData;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final RegexUtil usernameValidator = new RegexUtil("^[a-zA-Z0-9_]{1,255}$");
    private final RegexUtil passwordValidator = new RegexUtil("^.{8,255}$");

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validateUsername(String username) {
        if (username == null) {
            return false;
        }
        return usernameValidator.validate(username.trim());
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return passwordValidator.validate(password);
    }

    public UserDto registerUser(String username, String password) throws IllegalArgumentException {
        if (!validateUsername(username) || !validatePassword(password)) {
            throw new IllegalArgumentException("Invalid input");
        }

        Long userId = userData.createUser(username, encoder.encode(password));
        if (userId == null) {
            return null;
        }

        return userData.getUserDto(userId);
    }

    public UserDto authenticateUser(String username, String rawPassword) throws IllegalArgumentException {
        if (!validateUsername(username) || !validatePassword(rawPassword)) {
            throw new IllegalArgumentException("Invalid input");
        }

        String passwordHash = userData.getPasswordHash(username);
        if (encoder.matches(rawPassword, passwordHash)) {
            return userData.getUserDto(username);
        }

        return null;
    }
}
