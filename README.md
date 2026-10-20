# E-Commerce Platform (Java, JSP, Servlets, MySQL)

A web-based shopping site built with Servlets, JSP/JSTL and JDBC following the MVC pattern.

## Features
- User registration and login (passwords salted and hashed, session-fixation protection)
- Product catalogue with search by name or category
- Shopping cart stored in the session (add, update quantity, remove)
- Checkout that runs in a single database transaction: stock is checked and reduced, and everything rolls back if any item is out of stock
- Order history for each user
- Login required for cart, checkout and orders (`AuthFilter`)
- SQL injection safe (PreparedStatement everywhere) and XSS safe (`<c:out>` on all output)

## Tech Stack
Java 11, Servlet 4.0 / JSP 2.3, JSTL 1.2, JDBC, MySQL 8, Maven, Apache Tomcat 9

## Project Structure
```
├── pom.xml
├── database/schema.sql              # tables + 8 sample products
└── src/main
    ├── java/com/cyrostack/ecommerce
    │   ├── model/    User, Product, Cart, CartItem, Order, OrderItem
    │   ├── dao/      UserDAO, ProductDAO, OrderDAO
    │   ├── servlet/  Login, Register, Logout, ProductList, Cart, Checkout, Order
    │   ├── filter/   AuthFilter
    │   └── util/     DBConnection, PasswordUtil
    ├── resources/db.properties      # DB url / user / password
    └── webapp/                      # JSPs, CSS, WEB-INF
```

## Setup
1. Install JDK 11+, Maven, MySQL 8 and **Apache Tomcat 9** (Tomcat 10+ uses `jakarta.*` and will not work with this code).
2. Create the database:
   ```bash
   mysql -u root -p < database/schema.sql
   ```
3. Edit `src/main/resources/db.properties` with your MySQL username and password.
4. Build the WAR:
   ```bash
   mvn clean package
   ```
5. Copy `target/ecommerce.war` into Tomcat's `webapps` folder, start Tomcat and open
   `http://localhost:8080/ecommerce/`

(In IntelliJ/Eclipse you can also import it as a Maven project and run it on a Tomcat 9 server.)

## Author
Your Name - Cyrostack Internship Project
