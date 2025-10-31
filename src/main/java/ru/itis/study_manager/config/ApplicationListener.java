package ru.itis.study_manager.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.study_manager.dao.TaskDao;
import ru.itis.study_manager.dao.UserDao;
import ru.itis.study_manager.service.TaskService;
import ru.itis.study_manager.service.UserService;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(
                "userService", new UserService(new UserDao())
        );
        sce.getServletContext().setAttribute(
                "taskService", new TaskService(new TaskDao())
        );
    }
}
