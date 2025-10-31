package ru.itis.study_manager.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ru.itis.study_manager.entity.UserEntity;

public class Service {
    public UserEntity getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (UserEntity) session.getAttribute("user");
    }

    public void setCurrentUser(HttpServletRequest request, UserEntity user) {
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
    }

    public boolean isAuthorized(HttpServletRequest request) {
        return getCurrentUser(request) != null;
    }
}