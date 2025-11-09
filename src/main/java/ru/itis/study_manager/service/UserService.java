package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.converter.UserEntityToDtoConverter;
import ru.itis.study_manager.converter.UserModelToEntityConverter;
import ru.itis.study_manager.dao.UserDao;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.model.User;
import ru.itis.study_manager.util.HashUtil;
import ru.itis.study_manager.util.validator.PasswordValidator;
import ru.itis.study_manager.util.validator.UsernameValidator;
import ru.itis.study_manager.util.validator.Validator;

@RequiredArgsConstructor
public class UserService {
    private final UserDao dao;
    private final UserModelToEntityConverter modelToEntityConverter;
    private final UserEntityToDtoConverter entityToDtoConverter;
    private final Validator usernameValidator = new UsernameValidator();
    private final Validator passwordValidator = new PasswordValidator();

    public UserDto registerUser(User user) throws IllegalArgumentException {
        if (!validateUser(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        UserEntity entity = dao.create(modelToEntityConverter.convert(user));
        return entityToDtoConverter.convert(entity);
    }

    public UserDto authenticateUser(User user) throws IllegalArgumentException {
        if (!validateUser(user)) {
            throw new IllegalArgumentException("Invalid user data");
        }
        if (HashUtil.matches(user.getPassword(), dao.getPasswordHash(modelToEntityConverter.convert(user)))) {
            UserEntity entity = dao.getUser(modelToEntityConverter.convert(user));
            return entityToDtoConverter.convert(entity);
        }
        return null;
    }

    // TODO: move to converter
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validateUsername(String username) {
        return usernameValidator.validate(username);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validatePassword(String password) {
        return passwordValidator.validate(password);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validateUser(User user) {
        return validateUsername(user.getUsername()) && validatePassword(user.getPassword());
    }
}
