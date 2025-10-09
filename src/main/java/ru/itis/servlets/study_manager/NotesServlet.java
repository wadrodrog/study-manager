package ru.itis.servlets.study_manager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/notes")
public class NotesServlet extends HttpServlet {
    private static final String TEMPLATE_PATH = "/html/notes.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(TEMPLATE_PATH);
        dispatcher.forward(request, response);
    }
}
