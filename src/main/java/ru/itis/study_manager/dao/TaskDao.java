package ru.itis.study_manager.dao;

import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.entity.TaskStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends DaoManager {
    public TaskDao() {
        try {
            super.getStatement().executeUpdate("""
            create table if not exists tasks (
                task_id bigserial primary key,
                user_id bigserial not null,
                title varchar(255) not null,
                contents varchar,
                attachments varchar[] default '{}',
                status varchar not null,
                due date
            );
            """);
        } catch (SQLException e) {
            System.err.println("Error while initializing TaskData: " + e.getMessage());
        }
    }

    public void create(long userId, String title, String contents, Date due) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            insert into tasks (user_id, title, contents, status, due)
            values (?, ?, ?, 'Incomplete', ?);
            """)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, contents);
            preparedStatement.setDate(4, due);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<TaskEntity> getAll(long userId) {
        List<TaskEntity> tasks = new ArrayList<>();
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            select * from tasks where user_id = ?;
            """)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tasks.add(new TaskEntity(
                        resultSet.getLong("task_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        (String[]) resultSet.getArray("attachments").getArray(),
                        TaskStatus.of(resultSet.getString("status")),
                        resultSet.getDate("due")
                ));
            }
            return tasks;
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(long taskId, long userId) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            delete from tasks
            where task_id = ? and user_id = ?;
            """)) {
            preparedStatement.setLong(1, taskId);
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(long taskId, long userId, TaskStatus status) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            update tasks
            set status = ?
            where task_id = ? and user_id = ?;
            """)) {
            preparedStatement.setString(1, status.name);
            preparedStatement.setLong(2, taskId);
            preparedStatement.setLong(3, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(long taskId, long userId, Date due) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            update tasks
            set due = ?
            where task_id = ? and user_id = ?;
            """)) {
            preparedStatement.setDate(1, due);
            preparedStatement.setLong(2, taskId);
            preparedStatement.setLong(3, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}