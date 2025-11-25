package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.converter.ScheduleConverter;
import ru.itis.study_manager.dao.ScheduleDao;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.ScheduleEntity;
import ru.itis.study_manager.model.Schedule;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleDao dao;
    private final ScheduleConverter converter;

    public ScheduleEntity get(UserDto user, long eventId) {
        if (user == null) {
            return null;
        }
        return dao.get(eventId, user.getUserId());
    }

    public List<ScheduleEntity> getAll(UserDto user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return dao.getAll(user.getUserId());
    }

    public void create(Schedule schedule) {
        dao.create(converter.convert(schedule));
    }

    public void update(Schedule schedule) {
        dao.update(converter.convert(schedule));
    }

    public void delete(Schedule schedule) {
        dao.delete(converter.convert(schedule));
    }
}
