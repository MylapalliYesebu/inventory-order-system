package com.yesebu.inventory.model;

public class Product {

    private int productId;
    private String productName;
    private double price;
    private int quantity;

    public Product(int productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
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
        if (quantity >= 0) {
            this.quantity = quantity;            
        }

    }

    @Override
    public String toString() {
        return "Product ID: " + productId + ", Name: " + productName + ", Price: " + price + ", Quantity: " + quantity;
    }
}