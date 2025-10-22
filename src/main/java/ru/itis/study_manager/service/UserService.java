package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.data.UserData;
import ru.itis.study_manager.dto.UserDto;

@RequiredArgsConstructor
public class UserService {
    private final UserData userData;

    public UserDto authenticateUser(String username, String password) {
        return userData.get(username, getPasswordHash(password));
    }

    public String getPasswordHash(String password) {
        return password;
    }
}
