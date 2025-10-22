package ru.itis.study_manager.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.service.UserService;
import ru.itis.study_manager.web.RegisterHtml;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService service;
    private RegisterHtml html;

    @Override
    public void init() {
        service = (UserService) getServletContext().getAttribute("userService");
        html = new RegisterHtml();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println(html.getPage());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeat_password");

        if (username == null || username.trim().isBlank()
                || password == null || password.isEmpty()) {
            resp.sendRedirect("/register?error=Username and password are required");
            return;
        }

        if (!password.equals(repeatPassword)) {
            resp.sendRedirect("/register?error=Passwords don't match");
            return;
        }

        UserDto user = service.registerUser(username, password);

        if (user == null) {
            resp.sendRedirect("/register?error=Username already exists");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/welcome");
        }
    }
}
