package ru.itis.servlets.study_manager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    private static final String TEMPLATE_PATH = "/html/welcome.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //response.setContentType("text/html;charset=UTF-8");
        //request.getRequestDispatcher("/jsp/base.jsp").forward(request, response);
        RequestDispatcher dispatcher = request.getRequestDispatcher(TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }
}
