package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserEntity {
    private Long userId;
    private String username;
    private String passwordHash;
}
