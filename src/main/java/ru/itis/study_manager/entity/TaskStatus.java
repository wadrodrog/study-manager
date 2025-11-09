package ru.itis.study_manager.entity;

public enum TaskStatus {
    UNKNOWN("Неизвестно"),
    INCOMPLETE("Не завершено"),
    IN_PROGRESS("В процессе"),
    COMPLETE("Завершено");

    public final String ru;

    TaskStatus(String ru) {
        this.ru = ru;
    }
}
