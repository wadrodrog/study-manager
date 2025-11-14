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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Page(req, resp).show("Авторизация", "login", List.of("form"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(username, password);

        try {
            UserDto authenticatedUser = service.authenticateUser(user);
            if (authenticatedUser == null) {
                resp.sendRedirect("/login?error=Invalid credentials");
                return;
            }
            new Page(req).setCurrentUser(authenticatedUser);
            resp.sendRedirect("/dashboard");
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        }
    }
}
