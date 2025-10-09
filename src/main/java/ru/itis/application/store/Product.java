package ru.itis.application.store;

public class Product {
    private final int id;
    private final String name;
    private final Double price;

    public Product(int id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return price != null;
    }

    public String toHTML(boolean isInCart, String currentPath) {
        return """
        <div class="product">
            <img src="/img/store/%d.jpg">
            <div class="info">
                <h3>%s</h3>
                <p>%s руб.</p>
                <form method="post" action="/cart">
                    <div>
                        <input type="hidden" name="%sItem" value="%d">
                        <input type="hidden" name="currentPath" value="%s">
                        <input type="submit" value="%s"%s>
                    </div>
                </form>
            </div>
        </div>
        """.formatted(
                id, name,
                isAvailable() ? "%.2f".formatted(price) : "-",
                isInCart ? "remove" : "add", id,
                currentPath,
                isInCart ? "Убрать из корзины" : "В корзину",
                isAvailable() ? "" : " disabled"
        );
    }
}
