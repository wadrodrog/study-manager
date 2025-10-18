package ru.itis.servlets.store;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.application.store.Cart;
import ru.itis.application.store.Product;
import ru.itis.application.store.Store;
import ru.itis.study_manager.web.HtmlManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/store")
public class StoreServlet extends HttpServlet {
    private HtmlManager htmlManager = null;
    private Store store = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (htmlManager == null) {
            htmlManager = new HtmlManager("Магазин", "");
        }
        if (store == null) {
            store = new Store("store/products.csv");
        }

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<p><a href=\"/cart\">Корзина (%d)</a></p>\n".formatted(cart.size()));
        for (Product product : store.getProducts()) {
            sb.append(product.toHTML(cart.getItems().contains(product.getId()), "/store"));
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(htmlManager.generate(sb.toString()));
    }
}
