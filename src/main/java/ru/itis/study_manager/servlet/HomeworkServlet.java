package ru.itis.study_manager.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.entity.HomeworkEntity;
import ru.itis.study_manager.service.HomeworkService;
import ru.itis.study_manager.web.HomeworkHtml;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/homework")
public class HomeworkServlet extends HttpServlet {
    private HomeworkService service;
    private HomeworkHtml html;

    @Override
    public void init() {
        this.service = (HomeworkService) getServletContext().getAttribute("homeworkService");
        this.html = new HomeworkHtml();
    }

    private UserDto getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (UserDto) session.getAttribute("user");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = getUser(request);
        List<HomeworkEntity> homeworkList = service.getAll(user);
        String page = html.getPage(homeworkList);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = getUser(request);
        if (user == null) {
            response.sendError(403);
            return;
        }

        Long homeworkId;
        try {
            homeworkId = Long.parseLong(request.getParameter("homework_id"));
        } catch (NumberFormatException e) {
            homeworkId = null;
        }

        String disciplineName = request.getParameter("discipline_name");
        if (disciplineName.length() > 255) {
            response.sendError(413, "Discipline name must be less than 256 characters.");
            return;
        }

        String status = "Incomplete"; //request.getParameter("status");
        String contents = request.getParameter("contents");

        Date deadline = null;
        try {
            String deadlineParameter = request.getParameter("deadline");
            if (deadlineParameter != null && !deadlineParameter.isEmpty()) {
                deadline = Date.valueOf(deadlineParameter);
            }
        } catch (IllegalArgumentException e) {
            response.sendError(400, "Invalid date format for deadline.");
            return;
        }

        if (homeworkId == null) {
            service.add(user, disciplineName, contents, deadline);
        } else {
            service.update(user, homeworkId, disciplineName, status, contents, deadline);
        }

        response.sendRedirect("/homework");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserDto user = getUser(request);
        if (user == null) {
            response.sendError(403);
            return;
        }

        long homeworkId;
        try {
            homeworkId = Long.parseLong(request.getParameter("homework_id"));
        } catch (NumberFormatException e) {
            response.sendError(400, "Invalid Homework ID");
            return;
        }

        service.delete(user, homeworkId);
    }
}
