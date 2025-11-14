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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Page(req, resp).show(
                "Регистрация", "register",
                List.of("form"), List.of("repeat_password")
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = new User(username, password);

        try {
            UserDto registeredUser = service.registerUser(user);
            if (registeredUser == null) {
                resp.sendRedirect("/register?error=User already exists");
                return;
            }
            new Page(req).setCurrentUser(registeredUser);
            resp.sendRedirect("/dashboard");
        } catch (IllegalArgumentException e) {
            resp.sendError(400, e.getMessage());
        }
    }
}
