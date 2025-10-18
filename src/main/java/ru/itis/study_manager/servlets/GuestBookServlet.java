package ru.itis.study_manager.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.study_manager.web.HtmlManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/guest-book")
public class GuestBookServlet extends HttpServlet {
    private String errorMessage = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = """
                <form method="post" action="/guest-book">
                  <div>
                    <label>
                      Имя:
                      <input type="text" name="name" autocomplete="name" pattern="[a-zA-Zа-яА-ЯёЁ]{2,255}" required />
                    </label>
                  </div>
                  <div>
                    <label>
                      Email:
                      <input type="email" name="email" autocomplete="email" required />
                    </label>
                  </div>
                  <div>
                    <label>
                      Сообщение:
                      <textarea name="message" rows="5" cols="33" required></textarea>
                    </label>
                  </div>
                  <div>
                    <input type="submit" value="Отправить">
                  </div>
                </form>
                """;

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HtmlManager htmlManager = new HtmlManager("Гостевая книга", "");
        out.println(htmlManager.generate(content));

//        HttpSession session = request.getSession();
//        Integer visitCount = (Integer) session.getAttribute("visitCount");
//        if (visitCount == null) {
//            visitCount = 1;
//        } else {
//            visitCount++;
//        }
//        session.setAttribute("visitCount", visitCount);
//
//        String email = null;
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if ("email".equals(cookie.getName())) {
//                    email = cookie.getValue();
//                    System.out.println(email);
//                }
//            }
//        }
//
//        if (email != null) {
//            response.addCookie(new Cookie("email", email));
//        }
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
        out.println("<p>Количество запросов из этой сессии: " + session.getAttribute("visitCount") + "</p>");
        if (!errorMessage.isEmpty()) {
            out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
        }

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
