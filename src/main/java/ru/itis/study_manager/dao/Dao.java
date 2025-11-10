package ru.itis.study_manager.dao;

import ru.itis.study_manager.exception.DatabaseException;

import java.sql.*;

public class Dao {
    private Connection connection;

    public void createTable(String table, String... columns) throws DatabaseException {
        StringBuilder query = new StringBuilder();
        query.append("create table if not exists ").append(table).append(" (\n");
        for (String column : columns) {
            query.append("    ").append(column).append(",\n");
        }
        query.delete(query.length() - 2, query.length() - 1);
        query.append(");");

        try {
            getStatement().executeUpdate(query.toString());
        } catch (SQLException e) {
            throw new DatabaseException("Error while creating table: " + e.getMessage());
        }
    }

    public void createEnum(String name, String... elements) throws DatabaseException {
        StringBuilder query = new StringBuilder();
        query.append("create type ").append(name).append(" as enum (");
        for (String element : elements) {
            query.append("'").append(element).append("',");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(");");

        try {
            getStatement().executeUpdate(query.toString());
        } catch (SQLException ignored) {
            //throw new DatabaseException("Error while creating enum: " + e.getMessage());
        }
    }

    public Statement getStatement() throws DatabaseException {
        try {
            return getConnection().createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException("Unable to get statement: " + e.getMessage());
        }
    }

    public PreparedStatement getPreparedStatement(String query) throws DatabaseException {
        try {
            return getConnection().prepareStatement(query);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException("Unable to get prepared statement: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            String DATABASE_URL = "jdbc:postgresql://localhost:5432/study_manager";
            String DATABASE_USER = "postgres";
            String DATABASE_PASSWORD = "postgres";
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }
        return connection;
    }
}
