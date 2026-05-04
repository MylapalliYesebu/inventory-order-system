package com.yesebu.inventory.app;

import java.util.Scanner;

import com.yesebu.inventory.model.Product;
import com.yesebu.inventory.service.InventoryService;

public class InventoryApp {

    private final InventoryService inventoryService;
    private final Scanner scanner;

    public InventoryApp() {
        inventoryService = new InventoryService();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new InventoryApp().start();
    }

    private void start() {
        System.out.println("Inventory & Order Processing System Started");
        System.out.println("V2: SQLite database persistence enabled.");

        while (true) {
            showMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    inventoryService.viewProducts();
                    break;
                case 3:
                    updateProductQuantity();
                    break;
                case 4:
                    placeOrder();
                    break;
                case 5:
                    searchProducts();
                    break;
                case 6:
                    inventoryService.viewOrders();
                    break;
                case 7:
                    System.out.println("Exiting system. Thank you!!!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n1. Add Product");
        System.out.println("2. View Products");
        System.out.println("3. Update Product Quantity");
        System.out.println("4. Place Order");
        System.out.println("5. Search Products");
        System.out.println("6. View Orders");
        System.out.println("7. Exit");
    }

    private void addProduct() {
        String productName = readText("Enter Product Name: ");
        double price = readDouble("Enter Price: ");
        int quantity = readInt("Enter Quantity: ");

        try {
            inventoryService.addProduct(new Product(productName, price, quantity));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateProductQuantity() {
        int productId = readInt("Enter Product ID: ");
        int newQuantity = readInt("Enter New Quantity: ");
        inventoryService.updateProductQuantity(productId, newQuantity);
    }

    private void placeOrder() {
        System.out.println("Available Products:");
        inventoryService.viewProducts();
        int productId = readInt("Enter Product ID: ");
        int orderQuantity = readInt("Enter Order Quantity: ");
        inventoryService.placeOrder(productId, orderQuantity);
    }

    private void searchProducts() {
        String searchText = readText("Enter product name to search: ");
        inventoryService.searchProductsByName(searchText);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String readText(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }
}
