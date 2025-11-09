package ru.itis.study_manager.converter;

import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.model.User;
import ru.itis.study_manager.util.HashUtil;

public class UserModelToEntityConverter implements Converter<User, UserEntity> {
    @Override
    public UserEntity convert(User model) {
        return UserEntity.builder()
                .userId(model.getUserId())
                .username(model.getUsername())
                .passwordHash(HashUtil.hashPassword(model.getPassword()))
                .build();
    }
}
