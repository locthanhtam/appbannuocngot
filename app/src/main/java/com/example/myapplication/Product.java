package com.example.myapplication;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private long price;
    private int quantity;
    private int image; // Lưu ID của hình ảnh (R.drawable...)

    // Hàm khởi tạo 5 thông tin
    public Product(String id, String name, long price, int quantity, int image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    // Các hàm Get/Set
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getImage() { return image; }
    public void setImage(int image) { this.image = image; }
}