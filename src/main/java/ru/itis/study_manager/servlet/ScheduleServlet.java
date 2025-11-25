package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.ScheduleEntity;
import ru.itis.study_manager.exception.DatabaseException;
import ru.itis.study_manager.model.Schedule;
import ru.itis.study_manager.service.ScheduleService;
import ru.itis.study_manager.util.servlet.Page;

import java.io.IOException;
import java.util.List;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {
    private ScheduleService service;

    @Override
    public void init() {
        this.service = (ScheduleService) getServletContext().getAttribute("scheduleService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = new Page(req).getCurrentUser();

        List<ScheduleEntity> schedule = service.getAll(user);
        req.setAttribute("schedule", schedule);

        new Page(req, resp).show(
                "Расписание", "schedule", List.of("form", "tasks", "schedule"), List.of("schedule")
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto user = new Page(req).getCurrentUser();

        String weekday = req.getParameter("weekday");
        String name = req.getParameter("name");
        String place = req.getParameter("place");
        String notes = req.getParameter("notes");
        String timeStart = req.getParameter("time_start") + ":00";
        String timeEnd = req.getParameter("time_end") + ":00";

        if (":00".equals(timeStart)) {
            timeStart = null;
        }
        if (":00".equals(timeEnd)) {
            timeEnd = null;
        }

        if (timeStart != null && timeEnd != null && timeEnd.compareTo(timeStart) < 0) {
            resp.sendRedirect("/schedule?error=Invalid time range");
            return;
        }

        try {
            service.create(new Schedule(
                    null, user.getUserId(), weekday,
                    name, timeStart, timeEnd, place, notes
            ));
            resp.sendRedirect("/schedule");
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

        long eventId;
        try {
            eventId = Long.parseLong(req.getParameter("event_id"));
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid event_id");
            return;
        }

        service.delete(new Schedule(eventId, user.getUserId()));
    }
//
//    // For some unknown reason, to implement PATCH method you need to write this thing.
//    // Just overriding doPatch doesn't work.
//    @Override
//    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getMethod().equals("PATCH")) {
//            this.doPatch(req, resp);
//            return;
//        }
//        super.service(req, resp);
//    }
//
//    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        UserDto user = new Page(req).getCurrentUser();
//        if (user == null) {
//            resp.sendError(403);
//            return;
//        }
//
//        long taskId;
//        try {
//            taskId = Long.parseLong(req.getParameter("task_id"));
//        } catch (NumberFormatException e) {
//            resp.sendError(400, "Invalid task_id");
//            return;
//        }
//
//        String title = req.getParameter("title");
//        String contents = req.getParameter("contents");
//        String status = req.getParameter("status");
//        String priority = req.getParameter("priority");
//        String due = req.getParameter("due");
//
//        TaskEntity currentTask = service.get(user, taskId);
//        Task updatedTask = new Task(
//                currentTask.getTaskId(),
//                currentTask.getUserId(),
//                currentTask.getCreatedAt(),
//                title != null ? title : currentTask.getTitle(),
//                contents != null ? contents : currentTask.getContents(),
//                status != null ? status : currentTask.getStatus().name(),
//                priority != null ? priority : currentTask.getPriority() + "",
//                due != null ? due : (currentTask.getDue() == null ? null : currentTask.getDue().toString())
//        );
//
//        try {
//            service.update(updatedTask);
//            resp.setStatus(200);
//        } catch (Exception e) {
//            System.err.println("Error while updating task: " + e.getMessage());
//            resp.setStatus(400);
//        }
//    }
}
