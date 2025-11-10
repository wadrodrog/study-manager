package ru.itis.study_manager.util.task;

import lombok.Getter;

@Getter
public class TasksView {
    private final int maxPage;
    private int size = 10;
    private int page = 1;
    private TaskSort sort = TaskSort.CREATED_AT;
    private boolean descending = false;

    public TasksView(String size, String page, String sort, String descending, int totalCount) {
        setSize(size);
        maxPage = (totalCount - 1) / this.size + 1;
        setPage(page);
        setSort(sort);
        setDescending(descending);
    }

    private void setSize(String value) {
        try {
            this.size = Math.clamp(Integer.parseInt(value), 1, 1000);
        } catch (NumberFormatException ignored) {}
    }

    private void setPage(String value) {
        try {
            this.page = Math.clamp(Integer.parseInt(value), 1, maxPage);
        } catch (NumberFormatException ignored) {}
    }

    private void setSort(String value) {
        try {
            this.sort = TaskSort.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException ignored) {}
    }

    private void setDescending(String value) {
        this.descending = value != null;
    }
}
