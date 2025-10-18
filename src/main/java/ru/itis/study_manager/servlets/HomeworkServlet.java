package ru.itis.study_manager.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.data.HomeworkData;
import ru.itis.study_manager.web.HomeworkHtml;
import ru.itis.study_manager.web.HtmlManager;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/homework")
public class HomeworkServlet extends HttpServlet {
    private HomeworkData data = null;
    private HomeworkHtml html = null;

    private HomeworkData getData() {
        if (data == null) {
            try {
                data = new HomeworkData();
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
        return data;
    }

    private HomeworkHtml getHtml() {
        if (html == null) {
            html = new HomeworkHtml();
        }
        return html;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(getHtml().getHomeworkAddForm());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getData().add(
                request.getParameter("discipline_name"),
                request.getParameter("contents"),
                request.getParameter("deadline")
        );
        response.sendRedirect("/homework");
    }
}
