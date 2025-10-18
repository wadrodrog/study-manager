package ru.itis.servlets.store;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.application.store.Cart;
import ru.itis.study_manager.web.HtmlManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private HtmlManager htmlManager = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (htmlManager == null) {
            htmlManager = new HtmlManager("Спасибо за заказ!", "");
        }

        String sb = "<p><a href=\"/store\">Вернуться в магазин</a></p>\n";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(htmlManager.generate(sb));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        cart.order();

        session.setAttribute("cart", cart);

        response.sendRedirect("/order");
    }
}
