package com.yesebu.inventory.app;

import java.util.Scanner;

import com.yesebu.inventory.model.Product;
import com.yesebu.inventory.service.InventoryService;

public class InventoryApp {

    public static void main(String[] args) {
        InventoryService invServ = new InventoryService();
        Scanner sc = new Scanner(System.in);

        System.out.println("Inventory & Order Processing System Started");

        // Menu
        while (true) {
            System.out.println("\n1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Place Order");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter Product ID: ");
                    int pId = sc.nextInt();
                    System.out.println("Enter Product Name: ");
                    sc.nextLine(); // consume leftover newline
                    String pName = sc.nextLine();
                    System.out.println("Enter Price: ");
                    double pPrice = sc.nextDouble();
                    System.out.println("Enter Quantity: ");
                    int pQty = sc.nextInt();

                    invServ.addProduct(new Product(pId, pName, pPrice, pQty));
                    break;
                
                case 2:
                    invServ.viewProducts();
                    break;
                
                case 3:
                    System.out.println("Enter Product ID: ");
                    int proId = sc.nextInt();
                    System.out.println("Enter New Quantity: ");
                    int newQty = sc.nextInt();

                    invServ.updateProductQuantity(proId, newQty);
                    break;
                
                case 4:
                    System.out.println("Enter Order ID: ");
                    int ordId = sc.nextInt();
                    System.out.println("Enter Product ID: ");
                    int odrProId =sc.nextInt();
                    System.out.println("Enter Order Quantity: ");
                    int ordQty = sc.nextInt();

                    invServ.placeOrder(ordId, odrProId, ordQty);
                    break;

                case 5:
                    System.out.println("Exiting system. Thank you!!!");
                    sc.close();
                    return;
            
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}