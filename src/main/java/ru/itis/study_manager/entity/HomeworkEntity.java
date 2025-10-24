package ru.itis.study_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class HomeworkEntity {
    private Long homeworkId;
    private Long userId;
    private String disciplineName;
    private HomeworkStatus status;
    private String contents;
    private Date deadline;
    private String[] attachments;
}
