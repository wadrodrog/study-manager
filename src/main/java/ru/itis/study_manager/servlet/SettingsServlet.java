package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.model.User;
import ru.itis.study_manager.service.UserService;
import ru.itis.study_manager.util.servlet.Page;

import java.io.IOException;
import java.util.List;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Page(req, resp).show(
                "Настройки", "settings", List.of("form"), List.of("repeat_password")
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserDto user = new Page(req).getCurrentUser();
        if (user == null) {
            resp.sendError(403);
            return;
        }

        String currentPassword = req.getParameter("current_password");
        String newPassword = req.getParameter("new_password");
        String newUsername = req.getParameter("new_username");
        String theme = req.getParameter("theme");

        User userCheck = new User(user.getUsername(), currentPassword);
        if (service.authenticateUser(userCheck) == null) {
            resp.sendRedirect("/settings?error=Wrong password");
            return;
        }

        User updatedUser = new User(
                user.getUserId(),
                newUsername != null ? newUsername : user.getUsername(),
                newPassword,
                theme != null ? Short.parseShort(theme) : user.getTheme()
        );

        try {
            service.update(updatedUser);
            resp.sendRedirect("/settings?success");
        } catch (Exception e) {
            System.err.println("Error while updating user: " + e.getMessage());
            resp.sendRedirect("/settings?error");
        }
    }
}
