package com.cyrostack.ecommerce.servlet;

import com.cyrostack.ecommerce.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/products", ""})
public class ProductListServlet extends HttpServlet {
    private final ProductDAO products = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        try {
            req.setAttribute("products", products.search(q));
            req.setAttribute("q", q == null ? "" : q);
            req.getRequestDispatcher("/products.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Could not load products", e);
        }
    }
}
