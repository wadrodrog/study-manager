package ru.itis.study_manager.data;

import ru.itis.study_manager.dto.UserDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserData extends DataManager {
    public UserData() throws SQLException {
        super.getStatement().executeUpdate("""
            create table if not exists usr (
                id bigserial primary key,
                username varchar(255) not null unique,
                password_hash varchar(255) not null
            );
            """);
    }

    public UserDto get(String username, String passwordHash) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            select id, username from usr where username = ? and password_hash = ?;
            """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new UserDto(
                    resultSet.getLong("id"),
                    resultSet.getString("username")
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}