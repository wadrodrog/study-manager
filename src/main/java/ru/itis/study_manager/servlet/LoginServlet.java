package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.service.UserService;
import ru.itis.study_manager.util.ServletUtil;

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
        ServletUtil.showPage(req, resp, "Авторизация", "login", List.of("form"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            UserEntity user = service.authenticateUser(username, password);
            if (user == null) {
                resp.sendRedirect("/login?error=Wrong username or password");
                return;
            }
            ServletUtil.setCurrentUser(req, user);
            resp.sendRedirect("/dashboard");
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/login?error=" + e.getMessage());
        }
    }
}
