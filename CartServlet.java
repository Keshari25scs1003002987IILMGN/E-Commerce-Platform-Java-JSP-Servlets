package com.cyrostack.ecommerce.servlet;

import com.cyrostack.ecommerce.dao.ProductDAO;
import com.cyrostack.ecommerce.model.Cart;
import com.cyrostack.ecommerce.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductDAO products = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        String action = req.getParameter("action");
        int productId = parse(req.getParameter("productId"), -1);
        String redirect = "/cart";

        try {
            if ("add".equals(action)) {
                Product p = products.findById(productId);
                if (p == null || p.getStock() <= 0) {
                    session.setAttribute("flash", "Sorry, that product is not available.");
                } else {
                    cart.add(p, 1);
                    session.setAttribute("flash", "\"" + p.getName() + "\" added to cart.");
                }
                redirect = "/products";
            } else if ("update".equals(action)) {
                cart.update(productId, parse(req.getParameter("quantity"), 1));
            } else if ("remove".equals(action)) {
                cart.remove(productId);
            }
        } catch (SQLException e) {
            throw new ServletException("Cart update failed", e);
        }
        resp.sendRedirect(req.getContextPath() + redirect);
    }

    private static int parse(String s, int fallback) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return fallback; }
    }
}
