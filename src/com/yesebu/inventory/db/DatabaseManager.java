package com.yesebu.inventory.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_DIRECTORY = "data";
    private static final String DB_URL = "jdbc:sqlite:" + DB_DIRECTORY + "/inventory.db";

    public DatabaseManager() {
        initializeDatabase();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initializeDatabase() {
        File dataDirectory = new File(DB_DIRECTORY);
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS products (
                        product_id INTEGER PRIMARY KEY,
                        product_name TEXT NOT NULL,
                        price REAL NOT NULL CHECK (price >= 0),
                        quantity INTEGER NOT NULL CHECK (quantity >= 0)
                    )
                    """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS orders (
                        order_id INTEGER PRIMARY KEY,
                        product_id INTEGER NOT NULL,
                        order_quantity INTEGER NOT NULL CHECK (order_quantity > 0),
                        total_price REAL NOT NULL,
                        created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (product_id) REFERENCES products(product_id)
                    )
                    """);
        } catch (SQLException e) {
            throw new IllegalStateException("Unable to initialize database.", e);
        }
    }
}
