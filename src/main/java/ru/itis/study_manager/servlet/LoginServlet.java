package ru.itis.study_manager.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.data.UserData;
import ru.itis.study_manager.dto.UserDto;
import ru.itis.study_manager.service.UserService;
import ru.itis.study_manager.web.LoginHtml;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService service;
    private LoginHtml html;

    @Override
    public void init() {
        try {
            service = new UserService(new UserData());
            html = new LoginHtml();
        } catch (SQLException e) {
            System.err.printf("Error while initializing %s: %s%n", getServletName(), e.getMessage());
        }
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

        if (username == null || username.trim().isBlank()
                || password == null || password.isEmpty()) {
            resp.sendRedirect("/login?error=Username and password are required");
        }

        UserDto user = service.authenticateUser(username, password);

        if (user == null) {
            resp.sendRedirect("/login?error=Wrong username or password");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/welcome");
        }
    }
}
