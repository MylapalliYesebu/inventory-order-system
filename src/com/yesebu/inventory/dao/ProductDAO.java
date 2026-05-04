package com.yesebu.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.yesebu.inventory.db.DatabaseManager;
import com.yesebu.inventory.model.Product;

public class ProductDAO {

    private final DatabaseManager databaseManager;

    public ProductDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public int addProduct(Product product) {
        String sql = "INSERT INTO products (product_name, price, quantity) VALUES (?, ?, ?)";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to add product.");
        }

        return -1;
    }

    public List<Product> findAll() {
        String sql = "SELECT product_id, product_name, price, quantity FROM products ORDER BY product_id";
        List<Product> products = new ArrayList<>();

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                products.add(mapProduct(rs));
            }
        } catch (SQLException e) {
            System.out.println("Unable to load products.");
        }

        return products;
    }

    public Optional<Product> findById(int productId) {
        String sql = "SELECT product_id, product_name, price, quantity FROM products WHERE product_id = ?";

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapProduct(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to find product.");
        }

        return Optional.empty();
    }

    public List<Product> searchByName(String searchText) {
        String sql = """
                SELECT product_id, product_name, price, quantity
                FROM products
                WHERE LOWER(product_name) LIKE LOWER(?)
                ORDER BY product_id
                """;
        List<Product> products = new ArrayList<>();

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchText + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapProduct(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to search products.");
        }

        return products;
    }

    public boolean updateQuantity(int productId, int newQuantity) {
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = prepareUpdateQuantityStatement(conn, productId, newQuantity)) {
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateQuantity(Connection conn, int productId, int newQuantity) throws SQLException {
        try (PreparedStatement stmt = prepareUpdateQuantityStatement(conn, productId, newQuantity)) {
            return stmt.executeUpdate() > 0;
        }
    }

    private PreparedStatement prepareUpdateQuantityStatement(Connection conn, int productId, int newQuantity)
            throws SQLException {
        String sql = "UPDATE products SET quantity = ? WHERE product_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, newQuantity);
        stmt.setInt(2, productId);
        return stmt;
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("product_id"),
                rs.getString("product_name"),
                rs.getDouble("price"),
                rs.getInt("quantity")
        );
    }
}
