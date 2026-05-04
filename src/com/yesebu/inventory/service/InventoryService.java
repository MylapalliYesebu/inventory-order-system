package com.yesebu.inventory.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.yesebu.inventory.dao.OrderDAO;
import com.yesebu.inventory.dao.ProductDAO;
import com.yesebu.inventory.db.DatabaseManager;
import com.yesebu.inventory.model.Order;
import com.yesebu.inventory.model.Product;

public class InventoryService {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final DatabaseManager databaseManager;

    public InventoryService() {
        databaseManager = new DatabaseManager();
        productDAO = new ProductDAO(databaseManager);
        orderDAO = new OrderDAO(databaseManager);
    }

    // Add new product
    public void addProduct(Product p) {
        int productId = productDAO.addProduct(p);
        if (productId > 0) {
            System.out.println("Product added successfully. Product ID: " + productId);
        } else {
            System.out.println("Product could not be added.");
        }
    }

    // Display all products
    public void viewProducts() {
        List<Product> products = productDAO.findAll();
        if (products.isEmpty()) {
            System.out.println("No products available in inventory.");
            return;
        }
        for (Product p : products) {
            System.out.println(p); // Calls p.toString();
        }
    }

    public void searchProductsByName(String searchText) {
        List<Product> products = productDAO.searchByName(searchText);
        if (products.isEmpty()) {
            System.out.println("No matching products found.");
            return;
        }
        for (Product p : products) {
            System.out.println(p);
        }
    }

    // Updating product quantity
    public void updateProductQuantity(int productId, int newQuantity) {
        if (newQuantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }
        if (productDAO.updateQuantity(productId, newQuantity)) {
            System.out.println("Product quantity updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // Place Order
    public void placeOrder(int productId, int orderQuantity) {
        if (orderQuantity <= 0) {
            System.out.println("Order quantity must be greater than zero.");
            return;
        }

        Optional<Product> product = productDAO.findById(productId);
        if (product.isEmpty()) {
            System.out.println("Product not found.");
            return;
        }

        Product p = product.get();
        if (p.getQuantity() < orderQuantity) {
            System.out.println("Insufficient stock available.");
            return;
        }

        int updatedQuantity = p.getQuantity() - orderQuantity;
        Order order = new Order(productId, orderQuantity, p.getPrice());

        try (Connection conn = databaseManager.getConnection()) {
            conn.setAutoCommit(false);

            int orderId = orderDAO.addOrder(conn, order);
            boolean quantityUpdated = productDAO.updateQuantity(conn, productId, updatedQuantity);

            if (orderId <= 0 || !quantityUpdated) {
                conn.rollback();
                System.out.println("Order could not be placed.");
                return;
            }

            conn.commit();
            System.out.println("Order placed successfully. Order ID: " + orderId);
            System.out.println("Total Price: " + order.getTotalPrice());
            System.out.println("Remaining Stock: " + updatedQuantity);
        } catch (SQLException e) {
            System.out.println("Order could not be placed.");
        }
    }

    public void viewOrders() {
        List<Order> orders = orderDAO.findAll();
        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
