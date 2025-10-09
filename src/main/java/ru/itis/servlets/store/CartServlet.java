package ru.itis.servlets.store;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.application.store.Cart;
import ru.itis.application.store.Product;
import ru.itis.application.store.Store;
import ru.itis.web.HtmlManager;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private HtmlManager htmlManager = null;
    private Store store = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (htmlManager == null) {
            htmlManager = new HtmlManager("Корзина");
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
        sb.append("<a href=\"store\">Вернуться в магазин</a>\n");
        sb.append("""
                <form method="post" action="/order">
                    <div>
                        <input type="submit" value="Заказать"%s>
                    </div>
                </form>
                """.formatted(cart.size() > 0 ? "" : " disabled"));
        for (Integer id : cart.getItems()) {
            Product product = store.getProduct(id);
            sb.append(product.toHTML(true, "/cart"));
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(htmlManager.generate(sb.toString()));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String addItem = request.getParameter("addItem");
        String removeItem = request.getParameter("removeItem");

        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        if (addItem != null) {
            cart.add(Integer.parseInt(addItem));
        }
        if (removeItem != null) {
            cart.remove(Integer.parseInt(removeItem));
        }
        session.setAttribute("cart", cart);

        response.sendRedirect(request.getParameter("currentPath"));
    }
}
