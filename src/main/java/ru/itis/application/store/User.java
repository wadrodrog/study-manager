package ru.itis.application.store;

import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String email;
}
