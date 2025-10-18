package ru.itis.study_manager.entity;

public enum HomeworkStatus {
    NONE("None", "none"),
    INCOMPLETE("Incomplete", "incomplete"),
    IN_PROGRESS("In progress", "in-progress"),
    COMPLETE("Complete", "complete");

    public final String name;
    public final String css;

    HomeworkStatus(String name, String css) {
        this.name = name;
        this.css = css;
    }

    public static HomeworkStatus of(String string) {
        return switch (string) {
            case "Incomplete" -> HomeworkStatus.INCOMPLETE;
            case "In progress" -> HomeworkStatus.IN_PROGRESS;
            case "Complete" -> HomeworkStatus.COMPLETE;
            default -> HomeworkStatus.NONE;
        };
    }
}
