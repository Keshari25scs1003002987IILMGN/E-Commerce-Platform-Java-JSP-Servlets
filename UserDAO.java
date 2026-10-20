package com.cyrostack.ecommerce.dao;

import com.cyrostack.ecommerce.model.User;
import com.cyrostack.ecommerce.util.DBConnection;
import com.cyrostack.ecommerce.util.PasswordUtil;

import java.sql.*;

public class UserDAO {

    /** Returns false if the email is already registered. */
    public boolean register(String name, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (name, email, password_hash) VALUES (?, ?, ?)";
        try (Connection c = DBConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email.toLowerCase());
            ps.setString(3, PasswordUtil.hash(password));
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException duplicate) {
            return false;
        }
    }

    /** Returns the user if the credentials are valid, otherwise null. */
    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT id, name, email, password_hash FROM users WHERE email = ?";
        try (Connection c = DBConnection.get(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email.toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && PasswordUtil.verify(password, rs.getString("password_hash")))
                    return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
                return null;
            }
        }
    }
}
