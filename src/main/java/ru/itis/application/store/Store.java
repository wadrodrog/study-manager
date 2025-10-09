package ru.itis.application.store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Store {
    List<Product> products;

    public Store(String productsPath) {
        initProducts(productsPath);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    private void initProducts(String productsPath) {
        products = new ArrayList<>();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(productsPath);
        if (inputStream == null) {
            System.out.println("База данных продуктов " + productsPath + " не найдена.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            reader.readLine(); // skip csv header
            reader.lines().forEach(this::processLine);
        } catch (IOException e) {
            System.out.println("Чтение базы данных продуктов " + productsPath + " не удалось.");
        }
    }

    private void processLine(String line) {
        String[] parts = line.split(";");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        Double price;
        if ("NULL".equals(parts[2])) {
            price = null;
        } else {
            price = Double.parseDouble(parts[2]);
        }

        products.add(new Product(id, name, price));
    }
}
