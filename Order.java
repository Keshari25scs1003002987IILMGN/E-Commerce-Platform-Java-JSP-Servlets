package com.cyrostack.ecommerce.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id;
    private final BigDecimal total;
    private final String status;
    private final Timestamp createdAt;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(int id, BigDecimal total, String status, Timestamp createdAt) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public BigDecimal getTotal() { return total; }
    public String getStatus() { return status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public List<OrderItem> getItems() { return items; }
}
