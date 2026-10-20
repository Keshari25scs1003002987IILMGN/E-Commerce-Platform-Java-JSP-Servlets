package com.cyrostack.ecommerce.dao;

import com.cyrostack.ecommerce.model.Product;
import com.cyrostack.ecommerce.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    /** All products, or those whose name/category contains the search text. */
    public List<Product> search(String query) throws SQLException {
        String sql = "SELECT * FROM products";
        boolean filter = query != null && !query.trim().isEmpty();
        if (filter) sql += " WHERE name LIKE ? OR category LIKE ?";
        sql += " ORDER BY name";
        try (Connection c = DBConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            if (filter) {
                String like = "%" + query.trim() + "%";
                ps.setString(1, like);
                ps.setString(2, like);
            }
            List<Product> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        }
    }

    public Product findById(int id) throws SQLException {
        try (Connection c = DBConnection.get();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM products WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                rs.getString("category"), rs.getBigDecimal("price"), rs.getInt("stock"));
    }
}
