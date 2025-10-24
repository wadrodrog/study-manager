package ru.itis.study_manager.data;

import ru.itis.study_manager.entity.HomeworkEntity;
import ru.itis.study_manager.entity.HomeworkStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkData extends DataManager {
    public HomeworkData() {
        try {
            super.getStatement().executeUpdate("""
            create table if not exists homework (
                homework_id bigserial primary key,
                user_id bigserial not null,
                discipline_name varchar(255) not null,
                status varchar not null,
                contents varchar,
                deadline date,
                attachments varchar[] default '{}'
            );
            """);
        } catch (SQLException e) {
            System.err.println("Error while initializing UserData: " + e.getMessage());
        }
    }

    public void add(Long userId, String disciplineName, String contents, Date deadline) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            insert into homework (user_id, discipline_name, status, contents, deadline)
            values (?, ?, 'Incomplete', ?, ?);
            """)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, disciplineName);
            preparedStatement.setString(3, contents);
            preparedStatement.setDate(4, deadline);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<HomeworkEntity> getAll(long userId) {
        List<HomeworkEntity> homeworkList = new ArrayList<>();
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            select * from homework where user_id = ?;
            """)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                homeworkList.add(new HomeworkEntity(
                        resultSet.getLong("homework_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("discipline_name"),
                        HomeworkStatus.of(resultSet.getString("status")),
                        resultSet.getString("contents"),
                        resultSet.getDate("deadline"),
                        (String[]) resultSet.getArray("attachments").getArray()
                ));
            }
            return homeworkList;
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(long homeworkId, long userId) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            delete from homework
            where homework_id = ? and user_id = ?;
            """)) {
            preparedStatement.setLong(1, homeworkId);
            preparedStatement.setLong(2, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public void update(
            long homeworkId, long userId,
            String disciplineName, HomeworkStatus status, String contents, Date deadline
    ) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            update homework
            set discipline_name = ?, status = ?, contents = ?, deadline = ?
            where homework_id = ? and user_id = ?;
            """)) {
            preparedStatement.setString(1, disciplineName);
            preparedStatement.setString(2, status.name);
            preparedStatement.setString(3, contents);
            preparedStatement.setDate(4, deadline);
            preparedStatement.setLong(5, homeworkId);
            preparedStatement.setLong(6, userId);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}