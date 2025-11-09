package ru.itis.study_manager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {
    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);

        if (uri.equals("/")) {
            httpResponse.sendRedirect("/welcome");
            return;
        }

        if (
            session == null && !uri.equals("/welcome") && !uri.startsWith("/static/") &&
            !uri.startsWith("/css/") && !uri.startsWith("/js/") && !uri.startsWith("/img/") &&
            !uri.equals("/favicon.ico") && !uri.equals("/login") && !uri.equals("/register")
        ) {
            httpResponse.sendRedirect("/login");
            return;
        }

        chain.doFilter(request, response);
    }
}
