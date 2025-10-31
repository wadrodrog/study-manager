package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskEntity {
    private long taskId;
    private long userId;
    private String title;
    private String contents;
    private String[] attachments;
    private TaskStatus status;
    private Date due;
}
