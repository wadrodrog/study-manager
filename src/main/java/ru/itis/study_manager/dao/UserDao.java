package ru.itis.study_manager.dao;

import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends Dao {
    public UserDao() throws DatabaseException {
        createTable(
                "usr",
                "user_id bigserial primary key",
                "username varchar(32) not null unique",
                "password_hash varchar(60) not null"
        );
    }

    public UserEntity create(UserEntity entity) throws DatabaseException {
        String query = """
                insert into usr (username, password_hash)
                values (?, ?)
                on conflict (username) do nothing
                returning user_id;
                """;
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getPasswordHash());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            entity.setUserId(resultSet.getLong("user_id"));
            return entity;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public UserEntity getUser(UserEntity entity) throws DatabaseException {
        String query = "select user_id from usr where username = ?;";
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setString(1, entity.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            entity.setUserId(resultSet.getLong("user_id"));
            return entity;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public String getPasswordHash(UserEntity entity) throws DatabaseException {
        String query = "select password_hash from usr where username = ?;";
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setString(1, entity.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getString("password_hash");
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }
}
