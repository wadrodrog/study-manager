package ru.itis.study_manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Schedule {
    private Long eventId;
    private long userId;
    private String weekday;
    private String name;
    private String timeStart;
    private String timeEnd;
    private String place;
    private String notes;

    public Schedule(long eventId, long userId) {
        this.eventId = eventId;
        this.userId = userId;
    }
}
