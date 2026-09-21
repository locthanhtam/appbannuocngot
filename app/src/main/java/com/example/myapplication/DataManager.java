package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<Product> productList = new ArrayList<>();

    static {
        // Dữ liệu mẫu ban đầu: 1 món có sẵn như trong video
        productList.add(new Product("Coca-Cola Chai 1.5L", "Coca-Cola", 20000, 50));
    }

    public static int getTotalQuantity() {
        int total = 0;
        for (Product p : productList) {
            total += p.getQuantity();
        }
        return total;
    }

    public static long getTotalValue() {
        long total = 0;
        for (Product p : productList) {
            total += (p.getPrice() * p.getQuantity());
        }
        return total;
    }
}