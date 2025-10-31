package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEntity {
    private Long userId;
    private String username;
}
