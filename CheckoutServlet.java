package com.cyrostack.ecommerce.servlet;

import com.cyrostack.ecommerce.dao.OrderDAO;
import com.cyrostack.ecommerce.model.Cart;
import com.cyrostack.ecommerce.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final OrderDAO orders = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");
        String ctx = req.getContextPath();

        if (cart == null || cart.isEmpty()) {
            session.setAttribute("flash", "Your cart is empty.");
            resp.sendRedirect(ctx + "/cart");
            return;
        }
        try {
            int orderId = orders.placeOrder(user.getId(), cart);
            cart.clear();
            session.setAttribute("flash", "Order #" + orderId + " placed successfully!");
            resp.sendRedirect(ctx + "/orders");
        } catch (IllegalStateException e) {
            session.setAttribute("flash", e.getMessage() + " Please adjust your cart.");
            resp.sendRedirect(ctx + "/cart");
        } catch (SQLException e) {
            throw new ServletException("Checkout failed", e);
        }
    }
}
