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
                .createdAt(task.getCreatedAt())
                .title(task.getTitle().trim().isBlank() ? null : task.getTitle().trim().substring(0, Math.min(256, task.getTitle().trim().length())))
                .contents(task.getContents() == null ? "" : task.getContents())
                .attachments(task.getAttachments())
                .status(task.getStatus() == null ? TaskStatus.INCOMPLETE : TaskStatus.valueOf(task.getStatus().toUpperCase()))
                .priority(task.getPriority() == null ? (short) 0 : Short.parseShort(task.getPriority()))
                .due(task.getDue() == null || task.getDue().isEmpty() ? null : Date.valueOf(task.getDue()))
                .build();
    }
}
