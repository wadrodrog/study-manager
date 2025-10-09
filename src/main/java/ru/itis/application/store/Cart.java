package ru.itis.application.store;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Cart {
    private final Set<Integer> items;

    public Cart() {
        items = new HashSet<>();
    }

    public boolean add(Integer productId) {
        return items.add(productId);
    }

    public boolean remove(Integer productId) {
        return items.remove(productId);
    }

    public Set<Integer> getItems() {
        return items;
    }

    public int size() {
        return items.size();
    }

    public String order() {
        UUID uuid = UUID.randomUUID();
        String path = uuid + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Integer i : items) {
                writer.write(i + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        items.clear();

        return uuid.toString();
    }
}
