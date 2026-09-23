package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<Product> productList = new ArrayList<>();

    static {
        // Gắn đúng tên file ảnh ở thư mục drawable vào từng sản phẩm
        productList.add(new Product("Nước Ép Táo TH True Juice", "TH", 20500, 50, R.drawable.tao));
        productList.add(new Product("Nước Uống Lên Men Star", "Star", 29900, 30, R.drawable.komu));
        productList.add(new Product("Coca Cola 330ml", "Coca", 10000, 100, R.drawable.coca));
        productList.add(new Product("Pepsi Không Calo", "Pepsi", 10000, 80, R.drawable.pepsi));
    }

    public static int getTotalQuantity() {
        int total = 0;
        for (Product p : productList) { total += p.getQuantity(); }
        return total;
    }

    public static long getTotalValue() {
        long total = 0;
        for (Product p : productList) { total += (p.getPrice() * p.getQuantity()); }
        return total;
    }
}