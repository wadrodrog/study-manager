package ru.itis.study_manager.models;

public enum HomeworkStatus {
    NONE("none"), INCOMPLETE("incomplete"), IN_PROGRESS("in-progress"), COMPLETE("complete");

    HomeworkStatus(String css) {}

    public static HomeworkStatus of(String string) {
        return switch (string) {
            case "Incomplete" -> HomeworkStatus.INCOMPLETE;
            case "In progress" -> HomeworkStatus.IN_PROGRESS;
            case "Complete" -> HomeworkStatus.COMPLETE;
            default -> HomeworkStatus.NONE;
        };
    }
}
