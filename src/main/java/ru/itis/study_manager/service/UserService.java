package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.study_manager.dao.UserDao;
import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.util.RegexUtil;

@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;
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

    public UserEntity registerUser(String username, String password) throws IllegalArgumentException {
        if (!validateUsername(username) || !validatePassword(password)) {
            throw new IllegalArgumentException("Invalid input");
        }

        Long userId = dao.createUser(username, encoder.encode(password));
        if (userId == null) {
            return null;
        }

        return dao.getUser(userId);
    }

    public UserEntity authenticateUser(String username, String rawPassword) throws IllegalArgumentException {
        if (!validateUsername(username) || !validatePassword(rawPassword)) {
            throw new IllegalArgumentException("Invalid input");
        }

        String passwordHash = dao.getPasswordHash(username);
        if (encoder.matches(rawPassword, passwordHash)) {
            return dao.getUser(username);
        }

        return null;
    }

    public void updateTheme(UserEntity user, short theme) throws IllegalArgumentException, IllegalStateException {
        if (user == null) {
            throw new IllegalStateException("Not authorized");
        }
        if (theme < 0 || theme > 2) {
            throw new IllegalArgumentException("Invalid theme");
        }
        dao.updateTheme(user.getUserId(), theme);
        user.setTheme(theme);
    }
}
