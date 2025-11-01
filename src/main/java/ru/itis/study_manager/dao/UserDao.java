package ru.itis.study_manager.dao;

import ru.itis.study_manager.entity.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends DaoManager {
    public UserDao() {
        try {
            super.getStatement().executeUpdate("""
                    create table if not exists usr (
                        user_id bigserial primary key,
                        username varchar(255) not null unique,
                        password_hash varchar(255) not null,
                        theme smallint not null check (theme > -1 and theme < 3) default 0
                    );
                    """);
        } catch (SQLException e) {
            System.err.println("Error while initializing UserData: " + e.getMessage());
        }
    }

    public Long createUser(String username, String passwordHash) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            insert into usr (username, password_hash)
            values (?, ?)
            on conflict (username) do nothing
            returning user_id;
            """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getLong("user_id");
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public UserEntity getUser(long userId) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
                select username from usr where user_id = ?;
                """)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new UserEntity(
                    userId,
                    resultSet.getString("username"),
                    (short) 0
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public UserEntity getUser(String username) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
                select user_id, theme from usr where username = ?;
                """)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return new UserEntity(
                    resultSet.getLong("user_id"),
                    username,
                    resultSet.getShort("theme")
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getPasswordHash(String username) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            select password_hash from usr where username = ?;
            """)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return resultSet.getString("password_hash");
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void updateTheme(long userId, short theme) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
                update usr
                set theme = ?
                where user_id = ?;
                """)) {
            preparedStatement.setShort(1, theme);
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}