package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.data.TaskData;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.entity.TaskStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TaskService extends Service {
    private final TaskData data;

    public List<TaskEntity> getAll(UserDto user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return data.getAll(user.getUserId());
    }

    public void create(UserDto user, String title, String contents, Date due) {
        data.create(user.getUserId(), title, contents, due);
    }

    public void updateDue(UserDto user, long taskId, String dueString) {
        Date due = null;
        if (!dueString.isEmpty()) {
            try {
                due = Date.valueOf(dueString);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid due date");
            }
        }
        data.update(taskId, user.getUserId(), due);
    }

    public void updateStatus(UserDto user, long taskId, String statusString) {
        TaskStatus status = TaskStatus.of(statusString);
        if (status == TaskStatus.NONE) {
            throw new IllegalArgumentException("Invalid status");
        }
        data.update(taskId, user.getUserId(), status);
    }

    public void delete(UserDto user, long taskId) {
        data.delete(taskId, user.getUserId());
    }
}
