package ru.itis.study_manager.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.data.HomeworkData;
import ru.itis.study_manager.entity.HomeworkStatus;
import ru.itis.study_manager.web.HomeworkHtml;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(getHtml().getPage(getData().getAll()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String disciplineName = request.getParameter("discipline_name");
        String status = "Incomplete"; //request.getParameter("status");
        String contents = request.getParameter("contents");
        String deadline = request.getParameter("deadline");
        if (id == null) {
            getData().add(disciplineName, contents, deadline);
        } else {
            getData().update(
                    Integer.parseInt(id), disciplineName, HomeworkStatus.of(status),
                    contents, deadline
            );
        }
        response.sendRedirect("/homework");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        getData().delete(Integer.parseInt(request.getParameter("id")));
    }
}
