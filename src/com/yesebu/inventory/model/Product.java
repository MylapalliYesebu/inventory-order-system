package com.yesebu.inventory.model;

public class Product {

    private int productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(String productName, double price, int quantity) {
        this(0, productName, price, quantity);
    }

    public Product(int productId, String productName, double price, int quantity) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.productId = productId;
        this.productName = productName.trim();
        this.price = price;
        this.quantity = quantity;
    }

    // Getters
    public int getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + productName + ", Price: " + price + ", Quantity: " + quantity;
    }
}
