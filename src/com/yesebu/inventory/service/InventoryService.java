package com.yesebu.inventory.service;

import java.util.HashMap;
import java.util.Map;

import com.yesebu.inventory.model.Product;
import com.yesebu.inventory.model.Order;

public class InventoryService {

    private Map<Integer, Product> productMap;

    public InventoryService() {
        productMap = new HashMap<>();
    }

    // Add new product
    public void addProduct(Product p) {
        if (productMap.containsKey(p.getProductId())) {
            System.out.println("Product with this ID already exists.");   
        }
        else {
            productMap.put(p.getProductId(), p);
            System.out.println("Product added successfully.");
        }
    }

    // Display all products
    public void viewProducts() {
        if (productMap.isEmpty()) {
            System.out.println("No products available in inventory.");
            return;
        }
        for (Product p : productMap.values()) {
            System.out.println(p); // Calls p.toString();
        }
    }

    // Updating product quantity
    public void updateProductQuantity(int productId, int newQuantity) {
        if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }
        Product p = productMap.get(productId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        p.setQuantity(newQuantity);
        System.out.println("Product quantity updated successfully.");
    }

    // Place Order
    public void placeOrder(int orderId, int productId, int orderQuantity) {
        if (orderQuantity <= 0) {
            System.out.println("Order quantity must be greater than zero.");
            return;
        }
        Product p = productMap.get(productId);
        if (p == null) {
            System.out.println("Product not found.");
            return;
        }
        if (p.getQuantity() < orderQuantity) {
            System.out.println("Insufficient stock available.");
            return;
        }

        // reduce stock
        p.setQuantity(p.getQuantity() - orderQuantity);

        // create order
        Order order = new Order(orderId, productId, orderQuantity, p.getPrice());

        System.out.println("Order placed successfully.");
        System.out.println(order);
    }
}