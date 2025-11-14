package ru.itis.study_manager.converter;

import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.UserEntity;

public class UserEntityToDtoConverter implements Converter<UserEntity, UserDto> {
    @Override
    public UserDto convert(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return UserDto.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .theme(entity.getTheme())
                .build();
    }
}
