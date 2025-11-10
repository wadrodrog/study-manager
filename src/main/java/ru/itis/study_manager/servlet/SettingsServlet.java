package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.service.UserService;
import ru.itis.study_manager.util.servlet.Page;

import java.io.IOException;

@WebServlet("/settings")
public class SettingsServlet extends HttpServlet {
    private UserService service;

    @Override
    public void init() {
        this.service = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new Page(req, resp).show("Настройки", "settings");
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

//    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        UserEntity user = ServletUtil.getCurrentUser(req);
//        if (user == null) {
//            resp.sendError(403);
//            return;
//        }
//
//        String themeString = req.getParameter("theme");
//        if (themeString == null) {
//            resp.sendError(400, "Invalid theme");
//            return;
//        }
//
//        short theme = switch (themeString) {
//            case "auto" -> 0;
//            case "light" -> 1;
//            case "dark" -> 2;
//            default -> -1;
//        };
//        if (theme < 0) {
//            resp.sendError(400, "Invalid theme");
//            return;
//        }
//
//        service.updateTheme(user, theme);
//        ServletUtil.setCurrentUser(req, user);
//        resp.setStatus(200);
//    }
}
