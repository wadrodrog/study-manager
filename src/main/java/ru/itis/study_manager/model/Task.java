package ru.itis.study_manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Task {
    private Long taskId;
    private long userId;
    private String title;
    private String contents;
    private String[] attachments;
    private String status;
    private String due;

    public Task(long taskId, long userId) {
        this.taskId = taskId;
        this.userId = userId;
    }
}
