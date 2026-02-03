package com.yesebu.inventory.model;

public class Order {

    private int orderId;
    private int productId;
    private int orderQuantity;
    private double totalPrice;

    public Order(int orderId, int productId, int orderQuantity, double pricePerUnit) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.totalPrice = orderQuantity * pricePerUnit;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }
    public int getProductId() {
        return productId;
    }
    public int getOrderQuantity() {
        return orderQuantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId + ", Product ID: " + productId + ", Quantity: " + orderQuantity + ", Total Price: " + totalPrice;
    }
}