package ru.itis.study_manager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

@WebFilter("/*")
public class LogFilter extends HttpFilter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;

        HttpSession session = httpRequest.getSession(false);
        String sessionLog = session == null ? "" : "(...%s)".formatted(session.getId().substring(26));

        System.out.printf(
                "[%s] %s%s | %s %s %s%n",
                new Date(),
                httpRequest.getLocalAddr(),
                sessionLog,
                httpRequest.getProtocol(),
                httpRequest.getMethod(),
                httpRequest.getRequestURI()
        );

        super.doFilter(req, res, chain);
    }
}
