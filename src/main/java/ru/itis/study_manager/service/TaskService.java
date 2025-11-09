package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.converter.TaskConverter;
import ru.itis.study_manager.dao.TaskDao;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.model.Task;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TaskService {
    private final TaskDao dao;
    private final TaskConverter converter;

    public List<TaskEntity> getAll(UserDto user, int page, int size) {
        if (user == null) {
            return new ArrayList<>();
        }
        return dao.getAll(user.getUserId(), page, size);
    }

    public int getCount(UserDto user) {
        if (user == null) {
            return 0;
        }
        return dao.getCount(user.getUserId());
    }

    public void create(Task task) {
        dao.create(converter.convert(task));
    }

    public void update(Task task) {
        dao.update(converter.convert(task));
    }

    public void delete(Task task) {
        dao.delete(converter.convert(task));
    }
}
