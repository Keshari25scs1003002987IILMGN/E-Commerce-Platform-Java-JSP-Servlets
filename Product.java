package com.cyrostack.ecommerce.model;

import java.math.BigDecimal;

public class Product {
    private int id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private int stock;

    public Product(int id, String name, String description, String category, BigDecimal price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public BigDecimal getPrice() { return price; }
    public int getStock() { return stock; }
}
