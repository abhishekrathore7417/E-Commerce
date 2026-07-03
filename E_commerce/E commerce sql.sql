DROP DATABASE IF EXISTS minicart_db;
CREATE DATABASE minicart_db;
USE minicart_db;

-- 1. Products Table (is_active column ke saath)
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    description VARCHAR(255),
    image_url VARCHAR(255),
    category VARCHAR(50),
    is_active TINYINT(1) DEFAULT 1 -- 1 matlab Active, 0 matlab Deleted
);

-- 2. Cart Items Table
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- 3. Orders Table (Checkout logs ke liye)
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    total_items INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Fresh and updated mock data
INSERT INTO products (name, price, description, image_url, category, is_active) VALUES 
('Wireless Earbuds Pro', 1999.0, 'Active Noise Cancellation & 24hr Battery.', 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500', 'Audio', 1),
('Booster Boat Speaker', 2499.0, 'Waterproof bass speaker with RGB lights.', 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500', 'Audio', 1),
('Bluetooth Smartwatch', 3499.0, 'Fitness tracking, Calling feature & AMOLED display.', 'https://images.unsplash.com/photo-1508685096489-7aacd43bd3b1?w=500', 'Wearables', 1);

SELECT * FROM products;