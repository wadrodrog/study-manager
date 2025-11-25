package ru.itis.study_manager.converter;

import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.entity.TaskStatus;
import ru.itis.study_manager.model.Task;
import ru.itis.study_manager.util.StringUtil;

import java.sql.Date;

public class TaskConverter implements Converter<Task, TaskEntity> {
    @Override
    public TaskEntity convert(Task task) {
        return TaskEntity.builder()
                .taskId(task.getTaskId())
                .userId(task.getUserId())
                .createdAt(task.getCreatedAt())
                .title(StringUtil.isEmpty(task.getTitle()) ? null : StringUtil.truncate(task.getTitle(), 256))
                .contents(StringUtil.isEmpty(task.getContents()) ? "" : StringUtil.truncate(task.getContents(), 10000))
                .status(task.getStatus() == null ? TaskStatus.INCOMPLETE : TaskStatus.valueOf(task.getStatus().toUpperCase()))
                .priority(task.getPriority() == null ? (short) 0 : Short.parseShort(task.getPriority()))
                .due(task.getDue() == null || task.getDue().isEmpty() ? null : Date.valueOf(task.getDue()))
                .build();
    }
}
