package ru.itis.study_manager.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.entity.UserEntity;

import java.io.IOException;
import java.util.List;

public class ServletUtil {
    public static final String JSP_BASE = "/jsp/base.jsp";

    public static void showPage(
            HttpServletRequest request, HttpServletResponse response,
            String title, String content
    ) throws IOException, ServletException {
        showPage(request, response, title, content, null, null);
    }

    public static void showPage(
            HttpServletRequest request, HttpServletResponse response,
            String title, String content, List<String> css
    ) throws IOException, ServletException {
        showPage(request, response, title, content, css, null);
    }

    public static void showPage(
            HttpServletRequest request, HttpServletResponse response,
            String title, String content, List<String> css, List<String> js
    ) throws IOException, ServletException {
        ServletUtil.setAttributes(request, title, content, css, js);
        request.getRequestDispatcher(JSP_BASE).forward(request, response);
    }

    private static void setAttributes(
            HttpServletRequest request,
            String title, String content, List<String> css, List<String> js
    ) {
        request.setAttribute("title", title);
        request.setAttribute("content", content);
        if (css != null) {
            request.setAttribute("css", css);
        }
        if (js != null) {
            request.setAttribute("js", js);
        }
        request.setAttribute("authorized", isAuthorized(request));
    }

    public static boolean isAuthorized(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }

    public static UserEntity getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (UserEntity) session.getAttribute("user");
    }

    public static void setCurrentUser(HttpServletRequest request, UserEntity user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }
}
