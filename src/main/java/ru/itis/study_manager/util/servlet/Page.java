package ru.itis.study_manager.util.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.dto.UserDto;

import java.io.IOException;
import java.util.List;

public class Page {
    public static final String JSP_BASE = "/jsp/base.jsp";
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public Page(HttpServletRequest request) {
        this(request, null);
    }

    public Page(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void show(String title, String content) throws IOException, ServletException {
        show(title, content, null, null);
    }

    public void show(String title, String content, List<String> css) throws IOException, ServletException {
        show(title, content, css, null);
    }

    public void show(String title, String content, List<String> css, List<String> js) throws IOException, ServletException {
        if (response == null) {
            throw new IllegalStateException("Response not set");
        }

        request.setAttribute("title", title);
        request.setAttribute("content", content);
        if (css != null && !css.isEmpty()) {
            request.setAttribute("css", css);
        }
        if (js != null && !js.isEmpty()) {
            request.setAttribute("js", js);
        }

        UserDto user = getCurrentUser();
        request.setAttribute("authorized", user != null);
        if (user != null) {
            request.setAttribute("theme", user.getTheme());
        }

        request.getRequestDispatcher(JSP_BASE).forward(request, response);
    }

    public UserDto getCurrentUser() {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (UserDto) session.getAttribute("user");
    }

    public void setCurrentUser(UserDto user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
