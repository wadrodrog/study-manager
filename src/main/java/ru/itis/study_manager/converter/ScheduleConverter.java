package ru.itis.study_manager.converter;

import ru.itis.study_manager.entity.ScheduleEntity;
import ru.itis.study_manager.model.Schedule;

import java.sql.Time;

public class ScheduleConverter implements Converter<Schedule, ScheduleEntity> {
    @Override
    public ScheduleEntity convert(Schedule model) {
        return ScheduleEntity.builder()
                .eventId(model.getEventId())
                .userId(model.getUserId())
                .weekday(Short.parseShort(model.getWeekday()))
                .name(model.getName())
                .timeStart(model.getTimeStart() == null ? null : Time.valueOf(model.getTimeStart()))
                .timeEnd(model.getTimeEnd() == null ? null : Time.valueOf(model.getTimeEnd()))
                .place(model.getPlace())
                .notes(model.getNotes())
                .build();
    }
}
