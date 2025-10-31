package ru.itis.study_manager.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static ru.itis.study_manager.config.ApplicationListener.JSP_BASE;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "Обзор");
        req.setAttribute("content", "dashboard");
        req.setAttribute("authorized", true);
        req.getRequestDispatcher(JSP_BASE).forward(req, resp);
    }
}
