package ru.itis.study_manager.data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}