package ru.itis.study_manager.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.study_manager.data.HomeworkData;
import ru.itis.study_manager.data.UserData;
import ru.itis.study_manager.service.HomeworkService;
import ru.itis.study_manager.service.UserService;

@WebListener
public class ApplicationListener implements ServletContextListener {
    public static final String JSP_BASE = "/jsp/base.jsp";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute(
                "userService", new UserService(new UserData())
        );
        sce.getServletContext().setAttribute(
                "homeworkService", new HomeworkService(new HomeworkData())
        );
    }
}
