package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataManager {

    // Danh sách chứa toàn bộ sản phẩm của cửa hàng
    public static List<Product> productList = new ArrayList<>();

    // Khởi tạo sẵn các sản phẩm với đúng ảnh bạn đã thêm vào thư mục drawable
    static {
        // Gọi file coca.jpg
        productList.add(new Product("SP01", "Coca Cola 330ml", 10000, 100, R.drawable.coca));

        // Gọi file tao.jpg
        productList.add(new Product("SP02", "Nước Ép Táo TH True", 20500, 50, R.drawable.tao));

        // Gọi file komu.jpg
        productList.add(new Product("SP03", "Nước Uống Lên Men Star", 29900, 30, R.drawable.komu));

        // Gọi file pepsi.jpg
        productList.add(new Product("SP04", "Pepsi Không Calo", 10000, 80, R.drawable.pepsi));
    }
}