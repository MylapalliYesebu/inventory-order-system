package com.yesebu.inventory.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.yesebu.inventory.db.DatabaseManager;
import com.yesebu.inventory.model.Order;

public class OrderDAO {

    private final DatabaseManager databaseManager;

    public OrderDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public int addOrder(Order order) {
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = prepareAddOrderStatement(conn, order)) {
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Unable to add order.");
        }

        return -1;
    }

    public int addOrder(Connection conn, Order order) throws SQLException {
        try (PreparedStatement stmt = prepareAddOrderStatement(conn, order)) {
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }

        return -1;
    }

    private PreparedStatement prepareAddOrderStatement(Connection conn, Order order) throws SQLException {
        String sql = "INSERT INTO orders (product_id, order_quantity, total_price) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, order.getProductId());
        stmt.setInt(2, order.getOrderQuantity());
        stmt.setDouble(3, order.getTotalPrice());
        return stmt;
    }

    public List<Order> findAll() {
        String sql = "SELECT order_id, product_id, order_quantity, total_price FROM orders ORDER BY order_id";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = databaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(Order.fromTotalPrice(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("order_quantity"),
                        rs.getDouble("total_price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Unable to load orders.");
        }

        return orders;
    }
}
