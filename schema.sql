-- Run this once in MySQL:  mysql -u root -p < database/schema.sql
CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

CREATE TABLE IF NOT EXISTS users (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    email         VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    category    VARCHAR(50),
    price       DECIMAL(10,2) NOT NULL,
    stock       INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS orders (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT NOT NULL,
    total      DECIMAL(10,2) NOT NULL,
    status     VARCHAR(20) NOT NULL DEFAULT 'PLACED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    order_id     INT NOT NULL,
    product_id   INT NOT NULL,
    product_name VARCHAR(150) NOT NULL,
    price        DECIMAL(10,2) NOT NULL,
    quantity     INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO products (name, description, category, price, stock) VALUES
 ('Wireless Mouse',      '2.4 GHz wireless optical mouse',         'Electronics', 599.00, 50),
 ('Mechanical Keyboard', 'Blue-switch backlit keyboard',           'Electronics', 2499.00, 25),
 ('USB-C Hub',           '6-in-1 hub with HDMI and card reader',   'Electronics', 1299.00, 40),
 ('Java: The Complete Reference', 'Herbert Schildt, 12th edition', 'Books',       899.00, 30),
 ('Clean Code',          'A handbook of agile software craftsmanship', 'Books',   749.00, 20),
 ('Notebook Pack (5)',   'Ruled A5 notebooks',                     'Stationery',  249.00, 100),
 ('Laptop Backpack',     'Water-resistant, fits 15.6 inch laptop', 'Accessories', 1199.00, 35),
 ('Desk Lamp',           'LED lamp with adjustable brightness',    'Accessories', 699.00, 45);
