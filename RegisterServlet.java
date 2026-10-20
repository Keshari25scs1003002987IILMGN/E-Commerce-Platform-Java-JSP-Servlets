package com.cyrostack.ecommerce.servlet;

import com.cyrostack.ecommerce.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final UserDAO users = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = trim(req.getParameter("name"));
        String email = trim(req.getParameter("email"));
        String password = req.getParameter("password");

        String error = null;
        if (name.length() < 2) error = "Please enter your name.";
        else if (!email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) error = "Please enter a valid email.";
        else if (password == null || password.length() < 6) error = "Password must be at least 6 characters.";

        try {
            if (error == null && !users.register(name, email, password)) error = "That email is already registered.";
        } catch (SQLException e) {
            throw new ServletException("Registration failed", e);
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("name", name);
            req.setAttribute("email", email);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
            return;
        }
        req.getSession().setAttribute("flash", "Account created. Please log in.");
        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }
}
