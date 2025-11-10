package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
@Builder
public class TaskEntity {
    private Long taskId;
    private long userId;
    private Date createdAt;
    private String title;
    private String contents;
    private String[] attachments;
    private TaskStatus status;
    private short priority;
    private Date due;
}
