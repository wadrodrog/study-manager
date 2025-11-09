package ru.itis.study_manager.converter;

import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.entity.TaskStatus;
import ru.itis.study_manager.model.Task;

import java.sql.Date;

public class TaskConverter implements Converter<Task, TaskEntity> {
    @Override
    public TaskEntity convert(Task task) {
        return TaskEntity.builder()
                .taskId(task.getTaskId())
                .userId(task.getUserId())
                .title(task.getTitle())
                .contents(task.getContents())
                .attachments(task.getAttachments())
                .status(TaskStatus.valueOf(task.getStatus().toUpperCase()))
                .due(Date.valueOf(task.getDue()))
                .build();
    }
}
