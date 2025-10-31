package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.service.TaskService;
import ru.itis.study_manager.util.ServletUtil;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/tasks")
public class TasksServlet extends HttpServlet {
    private TaskService service;

    @Override
    public void init() {
        this.service = (TaskService) getServletContext().getAttribute("taskService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserEntity user = ServletUtil.getCurrentUser(req);
        List<TaskEntity> tasks = service.getAll(user);
        req.setAttribute("tasks", tasks);

        ServletUtil.showPage(
                req, resp,
                "Задачи", "tasks", List.of("form", "tasks"), List.of("tasks")
        );
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserEntity user = ServletUtil.getCurrentUser(req);
        if (user == null) {
            resp.sendError(403);
            return;
        }

        long taskId;
        try {
            taskId = Long.parseLong(req.getParameter("task_id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid task_id");
            return;
        }

        service.delete(user, taskId);
    }

    // For some unknown reason, to implement PATCH method you need to write this thing.
    // Just overriding doPatch doesn't work.
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("PATCH")) {
            this.doPatch(req, resp);
            return;
        }
        super.service(req, resp);
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserEntity user = ServletUtil.getCurrentUser(req);
        if (user == null) {
            resp.sendError(403);
            return;
        }

        long taskId;
        try {
            taskId = Long.parseLong(req.getParameter("task_id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid task_id");
            return;
        }

        // Due
        String due = req.getParameter("due");
        if (due != null) {
            try {
                service.updateDue(user, taskId, due);
                resp.setStatus(200);
                return;
            } catch (IllegalArgumentException e) {
                resp.sendError(400, e.getMessage());
                return;
            }
        }

        // Status
        String status = req.getParameter("status");
        if (status != null) {
            try {
                service.updateStatus(user, taskId, status);
                resp.setStatus(200);
                return;
            } catch (IllegalArgumentException e) {
                resp.sendError(400, e.getMessage());
                return;
            }
        }

        resp.sendError(400, "No parameters were provided");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserEntity user = ServletUtil.getCurrentUser(req);
        if (user == null) {
            resp.sendError(403);
            return;
        }

        String title = req.getParameter("title");
        if (title.length() > 255) {
            resp.sendError(413, "Title must be less than 256 characters.");
            return;
        }

        String contents = req.getParameter("contents");

        Date due = null;
        try {
            String dueParameter = req.getParameter("due");
            if (dueParameter != null && !dueParameter.isEmpty()) {
                due = Date.valueOf(dueParameter);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(400, "Invalid date format for deadline.");
            return;
        }

        service.create(user, title, contents, due);

        resp.sendRedirect("/tasks");
    }
}
