package com.example.myapplication;

public class Product {
    private String name;
    private String brand;
    private long price;
    private int quantity;
    private int imageResId;

    public Product(String name, String brand, long price, int quantity, int imageResId) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getBrand() { return brand; }
    public long getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
}