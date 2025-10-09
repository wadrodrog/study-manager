package ru.itis.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/feedback")
public class Feedback extends HttpServlet {
    private String errorMessage = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        String email = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                    System.out.println(email);
                }
            }
        }

        if (email != null) {
            response.addCookie(new Cookie("email", email));
        }

        sendForm(out, session);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        if (!validateName(name)) {
            errorMessage = "Имя слишком короткое! ";
        }
        if (!validateEmail(email)) {
            errorMessage += "Введите корректный email! ";
        }
        if (!validateMessage(message)) {
            errorMessage += "Слишком короткое сообщение! ";
        }
        if (!errorMessage.isEmpty()) {
            response.sendRedirect("feedback");
            return;
        }

        errorMessage = "";
        writeData(name, email, message);
        response.sendRedirect("thanks");
    }

    private void writeData(String name, String email, String message) throws IOException {
        String filename = "feedback_" + UUID.randomUUID() + ".txt";
        BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(Paths.get(filename)));
        out.write((name + '\n' + email + '\n' + message).getBytes());
        out.close();
    }

    private void sendForm(PrintWriter out, HttpSession session) {
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<h1>Обратная связь</h1>");
        out.println("<p>Количество запросов из этой сессии: " + session.getAttribute("visitCount") + "</p>");
        if (!errorMessage.isEmpty()) {
            out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
        }
        out.println("<form method=\"post\" action=\"/feedback\">");
        out.println("<div>");
        out.println("<label for=\"name\">Имя: </label>");
        out.println("<input type=\"text\" name=\"name\" id=\"form-name\" autocomplete=\"name\" required />");
        out.println("</div>");
        out.println("<div>");
        out.println("<label for=\"email\">Email: </label>");
        out.println("<input type=\"email\" name=\"email\" id=\"form-email\" autocomplete=\"email\" required />");
        out.println("</div>");
        out.println("<div>");
        out.println("<label for=\"message\" style=\"display: block;\">Сообщение: </label>");
        out.println("<textarea id=\"form-message\" name=\"message\" rows=\"5\" cols=\"33\" required></textarea>");
        out.println("</div>");
        out.println("<div>");
        out.println("<input id=\"form-send\" type=\"submit\" value=\"Отправить\">");
        out.println("</div>");
        out.println("</form>");
        out.println("</html>");
    }

    private boolean validateName(String name) {
        return name.length() > 1;
    }

    private boolean validateEmail(String email) {
        return email.contains("@");
    }

    private boolean validateMessage(String message) {
        return message.length() > 4;
    }
}
