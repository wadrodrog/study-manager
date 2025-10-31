package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.entity.UserEntity;
import ru.itis.study_manager.service.UserService;

import java.io.IOException;
import java.util.List;

import static ru.itis.study_manager.config.ApplicationListener.JSP_BASE;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Авторизация");
        req.setAttribute("css", List.of("form"));
        req.setAttribute("content", "login");
        req.setAttribute("authorized", service.isAuthorized(req));
        req.getRequestDispatcher(JSP_BASE).forward(req, resp);
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
            service.setCurrentUser(req, user);
            resp.sendRedirect("/dashboard");
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/login?error=" + e.getMessage());
        }
    }
}
