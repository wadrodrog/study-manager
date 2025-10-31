package ru.itis.study_manager.dao;

import java.sql.*;

public class DaoManager {
    public Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException, ClassNotFoundException {
        return getConnection().prepareStatement(query);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String DATABASE_URL = "jdbc:postgresql://localhost:5432/study_manager";
        String DATABASE_USER = "postgres";
        String DATABASE_PASSWORD = "postgres";
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
