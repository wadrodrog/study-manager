package ru.itis.study_manager.dao;

import ru.itis.study_manager.entity.ScheduleEntity;
import ru.itis.study_manager.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao extends Dao {
    public ScheduleDao() {
        createTable(
                "schedule",
                "event_id bigserial primary key",
                "user_id bigserial not null",
                "weekday smallint not null default 0 check (weekday > 0 and weekday < 8)",
                "name varchar(256) not null",
                "time_start time",
                "time_end time",
                "place varchar(256)",
                "notes varchar(256)"
        );
    }

    public void create(ScheduleEntity entity) {
        String query = """
                insert into schedule (user_id, weekday, name, time_start, time_end, place, notes)
                values (?, ?, ?, ?, ?, ?, ?);
                """;
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setLong(1, entity.getUserId());
            preparedStatement.setShort(2, entity.getWeekday());
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setTime(4, entity.getTimeStart());
            preparedStatement.setTime(5, entity.getTimeEnd());
            preparedStatement.setString(6, entity.getPlace());
            preparedStatement.setString(7, entity.getNotes());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public ScheduleEntity get(long eventId, long userId) {
        String query = """
                select
                    weekday, name, time_start, time_end, place, notes
                from schedule where event_id = ? and user_id = ?;
                """;

        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setLong(1, eventId);
            preparedStatement.setLong(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new ScheduleEntity(
                        eventId,
                        userId,
                        resultSet.getShort("weekday"),
                        resultSet.getString("name"),
                        resultSet.getTime("time_start"),
                        resultSet.getTime("time_end"),
                        resultSet.getString("place"),
                        resultSet.getString("notes")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public List<ScheduleEntity> getAll(long userId) {
        List<ScheduleEntity> schedule = new ArrayList<>();

        String query = """
                select
                    event_id, user_id, weekday, name, time_start, time_end, place, notes
                from schedule where user_id = ? order by weekday, time_start, time_end, name;
                """;

        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                schedule.add(new ScheduleEntity(
                        resultSet.getLong("event_id"),
                        resultSet.getLong("user_id"),
                        resultSet.getShort("weekday"),
                        resultSet.getString("name"),
                        resultSet.getTime("time_start"),
                        resultSet.getTime("time_end"),
                        resultSet.getString("place"),
                        resultSet.getString("notes")
                ));
            }
            return schedule;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public void update(ScheduleEntity entity) {
        String query = """
                update schedule
                set weekday = ?, name = ?, time_start = ?, time_end = ?, place = ?, notes = ?
                where event_id = ? and user_id = ?;
                """;
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setShort(1, entity.getWeekday());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setTime(3, entity.getTimeStart());
            preparedStatement.setTime(4, entity.getTimeEnd());
            preparedStatement.setString(5, entity.getPlace());
            preparedStatement.setString(6, entity.getNotes());
            preparedStatement.setLong(7, entity.getEventId());
            preparedStatement.setLong(8, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }

    public void delete(ScheduleEntity entity) {
        String query = "delete from schedule where event_id = ? and user_id = ?;";
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setLong(1, entity.getEventId());
            preparedStatement.setLong(2, entity.getUserId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing query: " + e.getMessage());
        }
    }
}