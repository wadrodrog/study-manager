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
import java.util.Objects;

import static ru.itis.study_manager.config.ApplicationListener.JSP_BASE;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Регистрация");
        req.setAttribute("css", List.of("form"));
        req.setAttribute("content", "register");
        req.setAttribute("authorized", service.isAuthorized(req));
        req.getRequestDispatcher(JSP_BASE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");

        if (!Objects.equals(password, repeatPassword)) {
            resp.sendRedirect("/register?error=Passwords do not match");
            return;
        }

        try {
            UserEntity user = service.registerUser(username, password);
            if (user == null) {
                resp.sendRedirect("/register?error=Username already exists");
                return;
            }
            service.setCurrentUser(req, user);
            resp.sendRedirect("/dashboard");
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/register?error=" + e.getMessage());
        }
    }
}
