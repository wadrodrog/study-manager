package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@AllArgsConstructor
@Builder
public class ScheduleEntity {
    private Long eventId;
    private long userId;
    private short weekday;
    private String name;
    private Time timeStart;
    private Time timeEnd;
    private String place;
    private String notes;
}
