package com.yesebu.inventory.model;

public class Order {

    private int orderId;
    private int productId;
    private int orderQuantity;
    private double totalPrice;

    public Order(int productId, int orderQuantity, double pricePerUnit) {
        this(0, productId, orderQuantity, orderQuantity * pricePerUnit, true);
    }

    public Order(int orderId, int productId, int orderQuantity, double pricePerUnit) {
        this(orderId, productId, orderQuantity, orderQuantity * pricePerUnit, true);
    }

    private Order(int orderId, int productId, int orderQuantity, double totalPrice, boolean totalPriceAlreadyCalculated) {
        if (orderQuantity <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than zero.");
        }
        this.orderId = orderId;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.totalPrice = totalPrice;
    }

    public static Order fromTotalPrice(int orderId, int productId, int orderQuantity, double totalPrice) {
        return new Order(orderId, productId, orderQuantity, totalPrice, true);
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
