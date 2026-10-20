package com.cyrostack.ecommerce.servlet;

import com.cyrostack.ecommerce.dao.OrderDAO;
import com.cyrostack.ecommerce.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private final OrderDAO orders = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        try {
            req.setAttribute("orders", orders.findByUser(user.getId()));
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Could not load orders", e);
        }
    }
}
