package ru.itis.study_manager.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.itis.study_manager.util.servlet.Page;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        boolean authorized = new Page(req, resp).isAuthorized();
        String uri = req.getRequestURI();

        if (uri.equals("/")) {
            resp.sendRedirect(authorized ? "/dashboard" : "/welcome");
            return;
        }

        if (!authorized && !uri.equals("/welcome") && !uri.startsWith("/static/") &&
            !uri.equals("/favicon.ico") && !uri.equals("/login") && !uri.equals("/register")) {
            resp.sendRedirect("/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }
}
