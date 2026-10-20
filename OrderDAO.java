package com.cyrostack.ecommerce.dao;

import com.cyrostack.ecommerce.model.*;
import com.cyrostack.ecommerce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /**
     * Places an order in ONE database transaction: reduce stock, create the order and its items.
     * If any product has too little stock, everything is rolled back.
     */
    public int placeOrder(int userId, Cart cart) throws SQLException {
        try (Connection c = DBConnection.get()) {
            c.setAutoCommit(false);
            try {
                int orderId;
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO orders (user_id, total) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, userId);
                    ps.setBigDecimal(2, cart.getTotal());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        orderId = keys.getInt(1);
                    }
                }
                try (PreparedStatement stock = c.prepareStatement(
                             "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?");
                     PreparedStatement item = c.prepareStatement(
                             "INSERT INTO order_items (order_id, product_id, product_name, price, quantity) VALUES (?,?,?,?,?)")) {
                    for (CartItem ci : cart.getItems()) {
                        Product p = ci.getProduct();
                        stock.setInt(1, ci.getQuantity());
                        stock.setInt(2, p.getId());
                        stock.setInt(3, ci.getQuantity());
                        if (stock.executeUpdate() == 0)
                            throw new IllegalStateException("Not enough stock for \"" + p.getName() + "\".");
                        item.setInt(1, orderId);
                        item.setInt(2, p.getId());
                        item.setString(3, p.getName());
                        item.setBigDecimal(4, p.getPrice());
                        item.setInt(5, ci.getQuantity());
                        item.executeUpdate();
                    }
                }
                c.commit();
                return orderId;
            } catch (SQLException | RuntimeException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public List<Order> findByUser(int userId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection c = DBConnection.get()) {
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT id, total, status, created_at FROM orders WHERE user_id = ? ORDER BY id DESC")) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next())
                        orders.add(new Order(rs.getInt("id"), rs.getBigDecimal("total"),
                                rs.getString("status"), rs.getTimestamp("created_at")));
                }
            }
            try (PreparedStatement ps = c.prepareStatement(
                    "SELECT product_name, price, quantity FROM order_items WHERE order_id = ?")) {
                for (Order o : orders) {
                    ps.setInt(1, o.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next())
                            o.getItems().add(new OrderItem(rs.getString("product_name"),
                                    rs.getBigDecimal("price"), rs.getInt("quantity")));
                    }
                }
            }
        }
        return orders;
    }
}
