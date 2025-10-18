package ru.itis.study_manager.entity;

import lombok.Data;

import java.util.Date;

@Data
public class HomeworkEntity {
    private int id;
    private String disciplineName;
    private HomeworkStatus status;
    private String contents;
    private Date deadline;
    private String[] attachments;

    public HomeworkEntity(int id, String disciplineName, HomeworkStatus status, String contents, Date deadline, String[] attachments) {
        this.id = id;
        this.disciplineName = disciplineName;
        this.status = status;
        this.contents = contents;
        this.deadline = deadline;
        this.attachments = attachments;
    }
}
