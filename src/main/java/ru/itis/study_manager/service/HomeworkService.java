package ru.itis.study_manager.service;

import lombok.RequiredArgsConstructor;
import ru.itis.study_manager.data.HomeworkData;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.HomeworkEntity;
import ru.itis.study_manager.entity.HomeworkStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HomeworkService {
    private final HomeworkData data;

    public List<HomeworkEntity> getAll(UserDto user) {
        if (user == null) {
            return new ArrayList<>();
        }
        return data.getAll(user.getUserId());
    }

    public void add(UserDto user, String disciplineName, String contents, Date deadline) {
        data.add(user.getUserId(), disciplineName, contents, deadline);
    }

    public void update(
            UserDto user, long homeworkId, String disciplineName, String status, String contents, Date deadline
    ) {
        data.update(
                homeworkId, user.getUserId(),
                disciplineName, HomeworkStatus.of(status),
                contents, deadline
        );
    }

    public void delete(UserDto user, long homeworkId) {
        data.delete(homeworkId, user.getUserId());
    }
}
