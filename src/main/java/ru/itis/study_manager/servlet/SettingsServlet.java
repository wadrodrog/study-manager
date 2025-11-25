package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
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
                "Настройки", "settings",
                List.of("settings", "form"),
                List.of("settings", "repeat_password")
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

        if (newPassword != null || newUsername != null) {
            User userCheck = new User(user.getUsername(), currentPassword);
            if (service.authenticateUser(userCheck) == null) {
                resp.sendRedirect("/settings?error=Wrong password");
                return;
            }
        }

        User updatedUser = new User(
                user.getUserId(),
                newUsername != null ? newUsername : user.getUsername(),
                newPassword
        );

        try {
            UserDto userDto = service.update(updatedUser);
            new Page(req, resp).setCurrentUser(userDto);
            resp.sendRedirect("/settings?success");
        } catch (Exception e) {
            resp.sendRedirect("/settings?error");
        }
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
        short theme;
        try {
            theme = Short.parseShort(req.getParameter("theme"));
        } catch (NumberFormatException e) {
            resp.sendRedirect("/settings?error");
            return;
        }

        Cookie cookie = new Cookie("theme", theme + "");
        resp.addCookie(cookie);
    }
}
