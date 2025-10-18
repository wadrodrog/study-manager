package ru.itis.study_manager.data;

import java.sql.*;

public class GuestBookData extends DataManager {
    public GuestBookData() throws SQLException {
        super.getStatement().executeUpdate("""
            create table if not exists guest_book (
                id bigserial primary key,
                name varchar(255) not null,
                email varchar(255) not null,
                message varchar(4096) not null,
            );
            """);
    }
}