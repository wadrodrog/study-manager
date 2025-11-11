package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.TaskEntity;
import ru.itis.study_manager.exception.DatabaseException;
import ru.itis.study_manager.model.Task;
import ru.itis.study_manager.service.TaskService;
import ru.itis.study_manager.util.servlet.Page;
import ru.itis.study_manager.util.task.TasksView;

import java.io.IOException;
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
        UserDto user = new Page(req).getCurrentUser();
        int totalCount = service.getCount(user);

        TasksView view = new TasksView(
                req.getParameter("size"),
                req.getParameter("page"),
                req.getParameter("sort"),
                req.getParameter("descending"),
                totalCount
        );

        List<TaskEntity> tasks = service.getAll(user, view);

        req.setAttribute("tasks", tasks);
        req.setAttribute("page", view.getPage());
        req.setAttribute("maxPage", view.getMaxPage());
        req.setAttribute("totalCount", totalCount);

        new Page(req, resp).show(
                "Задачи", "tasks",
                List.of("form", "tasks"), List.of("tasks")
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto user = new Page(req).getCurrentUser();

        String title = req.getParameter("title");
        String contents = req.getParameter("contents");
        String priority = req.getParameter("priority");
        String due = req.getParameter("due");
        String status = req.getParameter("status");

        try {
            service.create(new Task(
                    null, user.getUserId(), null,
                    title, contents, null, status,
                    priority, due
            ));
            resp.sendRedirect("/tasks");
        } catch (IllegalArgumentException | NullPointerException | DatabaseException e) {
            resp.sendError(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto user = new Page(req).getCurrentUser();
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

        service.delete(new Task(taskId, user.getUserId()));
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
        UserDto user = new Page(req).getCurrentUser();
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

        String title = req.getParameter("title");

        TaskEntity currentTask = service.get(user, taskId);
        Task updatedTask = new Task(
                currentTask.getTaskId(),
                currentTask.getUserId(),
                currentTask.getCreatedAt(),
                title != null ? title : currentTask.getTitle(),
                currentTask.getContents(),
                currentTask.getAttachments(),
                currentTask.getStatus().name(),
                currentTask.getPriority() + "",
                currentTask.getDue().toString()
        );

        try {
            service.update(updatedTask);
            resp.setStatus(200);
        } catch (Exception e) {
            System.err.println("Error while updating task: " + e.getMessage());
            resp.setStatus(400);
        }

//        String due = req.getParameter("due");
//        if (due != null) {
//            try {
//                service.updateDue(user, taskId, due);
//                resp.setStatus(200);
//                return;
//            } catch (IllegalArgumentException e) {
//                resp.sendError(400, e.getMessage());
//                return;
//            }
//        }
//
//        String status = req.getParameter("status");
//        if (status != null) {
//            try {
//                service.updateStatus(user, taskId, status);
//                resp.setStatus(200);
//                return;
//            } catch (IllegalArgumentException e) {
//                resp.sendError(400, e.getMessage());
//                return;
//            }
//        }
    }
}
