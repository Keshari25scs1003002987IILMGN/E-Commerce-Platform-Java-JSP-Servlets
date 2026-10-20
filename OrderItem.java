package com.cyrostack.ecommerce.model;

import java.math.BigDecimal;

public class OrderItem {
    private final String productName;
    private final BigDecimal price;
    private final int quantity;

    public OrderItem(String productName, BigDecimal price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() { return productName; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }
}
