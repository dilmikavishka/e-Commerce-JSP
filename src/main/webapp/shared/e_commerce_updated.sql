-- Create the database
CREATE DATABASE e_commerce;

-- Use the database
USE e_commerce;

-- Create the categories table
CREATE TABLE categories (
                            category_id VARCHAR(36) PRIMARY KEY,
                            category_name VARCHAR(100) NOT NULL,
                            description TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the products table
CREATE TABLE products (
                          product_id VARCHAR(36) PRIMARY KEY,
                          product_name VARCHAR(100) NOT NULL,
                          category_id VARCHAR(36),
                          price DECIMAL(10, 2) NOT NULL,
                          stock INT NOT NULL,
                          description TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

-- Create the users table
CREATE TABLE users (
                       user_id VARCHAR(36) PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create the orders table
CREATE TABLE orders (
                        order_id VARCHAR(36) PRIMARY KEY,
                        user_id VARCHAR(36) NOT NULL,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        total_price DECIMAL(10, 2),
                        status VARCHAR(50) DEFAULT 'Pending',
                        FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the order_details table to store multiple products in an order
CREATE TABLE order_details (
                               order_detail_id VARCHAR(36) PRIMARY KEY,
                               order_id VARCHAR(36) NOT NULL,
                               product_id VARCHAR(36) NOT NULL,
                               quantity INT NOT NULL,
                               price DECIMAL(10, 2) NOT NULL,
                               FOREIGN KEY (order_id) REFERENCES orders(order_id)  ON DELETE CASCADE ON UPDATE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES products(product_id)  ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create the cart table
CREATE TABLE cart (
                      cart_id VARCHAR(36) PRIMARY KEY,
                      user_id VARCHAR(36) NOT NULL,
                      added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the cart_items table to store multiple products in the cart
CREATE TABLE cart_items (
                            cart_item_id VARCHAR(36) PRIMARY KEY,
                            cart_id VARCHAR(36) NOT NULL,
                            product_id VARCHAR(36) NOT NULL,
                            quantity INT NOT NULL,
                            FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE ON UPDATE CASCADE,
                            FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE
);

SELECT o.order_id, o.user_id, o.order_date, o.total_price, o.status,
       od.order_detail_id, od.product_id, od.quantity, od.price
FROM orders o
         JOIN order_details od ON o.order_id = od.order_id
WHERE o.user_id = 'U003';


SELECT o.order_id, o.user_id, o.order_date, o.total_price, o.status,
       od.order_detail_id, od.product_id, od.quantity, od.price
FROM orders o
         JOIN order_details od ON o.order_id = od.order_id;
