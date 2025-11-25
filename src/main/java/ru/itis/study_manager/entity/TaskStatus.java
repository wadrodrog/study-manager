package ru.itis.study_manager.entity;

import lombok.Getter;

@Getter
public enum TaskStatus {
    INCOMPLETE("Не завершено"),
    IN_PROGRESS("В процессе"),
    COMPLETE("Завершено");

    private final String ru;

    TaskStatus(String ru) {
        this.ru = ru;
    }
}
