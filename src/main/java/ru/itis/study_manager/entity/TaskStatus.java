package ru.itis.study_manager.entity;

public enum TaskStatus {
    NONE("None", "none"),
    INCOMPLETE("Incomplete", "incomplete"),
    IN_PROGRESS("In progress", "in-progress"),
    COMPLETE("Complete", "complete");

    public final String name;
    public final String css;

    TaskStatus(String name, String css) {
        this.name = name;
        this.css = css;
    }

    public static TaskStatus of(String string) {
        return switch (string) {
            case "Incomplete" -> TaskStatus.INCOMPLETE;
            case "In progress" -> TaskStatus.IN_PROGRESS;
            case "Complete" -> TaskStatus.COMPLETE;
            default -> TaskStatus.NONE;
        };
    }
}
