package ru.itis.study_manager.dao;

import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.entity.TaskStatus;
import ru.itis.study_manager.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDao extends Dao {
    public TaskDao() {
        createEnum("task_status", "incomplete", "in_progress", "complete");
        createTable(
                "tasks",
                "task_id bigserial primary key",
                "user_id bigserial not null",
                "title varchar(255) not null",
                "contents varchar not null default ''",
                "attachments varchar[] not null default '{}'",
                "status task_status not null default 'incomplete'",
                "due date"
        );
    }

    public void create(TaskEntity entity) {
        String query = """
                insert into tasks (user_id, title, contents, status, due)
                values (?, ?, ?, 'Incomplete', ?);
                """;
        try (PreparedStatement preparedStatement = super.getPreparedStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getContents());
            preparedStatement.setDate(4, entity.getDue());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public List<TaskEntity> getAll(long userId, int page, int size) {
        page = Math.clamp(page, 1, Integer.MAX_VALUE);
        size = Math.clamp(size, 1, 100);
        List<TaskEntity> tasks = new ArrayList<>();

        String query = "select * from tasks where user_id = ? limit ? offset ?;";
        try (PreparedStatement preparedStatement = super.getPreparedStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setInt(2, size);
            preparedStatement.setInt(3, (page - 1) * size);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                tasks.add(new TaskEntity(
                        resultSet.getLong("task_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("contents"),
                        (String[]) resultSet.getArray("attachments").getArray(),
                        resultSet.getObject("status", TaskStatus.class),
                        resultSet.getDate("due")
                ));
            }
            return tasks;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public int getCount(long userId) {
        String query = "select count(*) from tasks where user_id = ?;";
        try (PreparedStatement preparedStatement = super.getPreparedStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public void update(TaskEntity entity) {
        String query = """
                update tasks
                set status = ?, due = ?
                where task_id = ? and user_id = ?;
                """;
        try (PreparedStatement preparedStatement = super.getPreparedStatement(query)) {
            preparedStatement.setObject(1, entity.getStatus());
            preparedStatement.setDate(2, entity.getDue());
            preparedStatement.setLong(3, entity.getTaskId());
            preparedStatement.setLong(4, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public void delete(TaskEntity entity) {
        String query = "delete from tasks where task_id = ? and user_id = ?;";
        try (PreparedStatement preparedStatement = super.getPreparedStatement(query)) {
            preparedStatement.setLong(1, entity.getTaskId());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }
}