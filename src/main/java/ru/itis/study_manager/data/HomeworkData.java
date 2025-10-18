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
    public HomeworkData() throws SQLException {
        super.getStatement().executeUpdate("""
            create table if not exists homework (
                id bigserial primary key,
                discipline_name varchar(255) not null,
                status varchar not null,
                contents varchar,
                deadline date,
                attachments varchar[] default '{}'
            );
            """);
    }

    public void add(String disciplineName, String contents, String deadline) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            insert into homework (discipline_name, status, contents, deadline)
            values (?, 'Incomplete', ?, ?);
            """)) {
            preparedStatement.setString(1, disciplineName);
            preparedStatement.setString(2, contents);
            preparedStatement.setDate(3, deadline.isEmpty() ? null : Date.valueOf(deadline));
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<HomeworkEntity> getAll() {
        List<HomeworkEntity> homeworkList = new ArrayList<>();
        try (ResultSet resultSet = getStatement().executeQuery("select * from homework;")) {
            while (resultSet.next()) {
                homeworkList.add(new HomeworkEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("discipline_name"),
                        HomeworkStatus.of(resultSet.getString("status")),
                        resultSet.getString("contents"),
                        resultSet.getDate("deadline"),
                        (String[]) resultSet.getArray("attachments").getArray()
                ));
            }
            return homeworkList;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public void delete(int id) {
        try (PreparedStatement preparedStatement = super.getPreparedStatement("""
            delete from homework where id = ?;
            """)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}