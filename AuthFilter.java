package com.cyrostack.ecommerce.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/** Pages that need a logged-in user. Anyone else is sent to the login page. */
@WebFilter(urlPatterns = {"/cart", "/checkout", "/orders"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            chain.doFilter(req, res);
            return;
        }
        request.getSession().setAttribute("flash", "Please log in to continue.");
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
