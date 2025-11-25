package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.ScheduleEntity;
import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.service.ScheduleService;
import ru.itis.study_manager.service.TaskService;
import ru.itis.study_manager.util.servlet.Page;
import ru.itis.study_manager.util.task.TasksView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private ScheduleService scheduleService;
    private TaskService taskService;

    @Override
    public void init() {
        this.scheduleService = (ScheduleService) getServletContext().getAttribute("scheduleService");
        this.taskService = (TaskService) getServletContext().getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = new Page(req).getCurrentUser();
        LocalDateTime datetime = LocalDateTime.now();

        List<ScheduleEntity> schedule = scheduleService.getAll(user, (short) datetime.getDayOfWeek().getValue());
        req.setAttribute("schedule", schedule);

        TasksView view = new TasksView("5", "1", "due", null, 5);
        List<TaskEntity> tasks = taskService.getAll(user, view);
        req.setAttribute("tasks", tasks);

        new Page(req, resp).show(
                "Обзор", "dashboard", List.of("tasks", "schedule"), List.of("time")
        );
    }
}
