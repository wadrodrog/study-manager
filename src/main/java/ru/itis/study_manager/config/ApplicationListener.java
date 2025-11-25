package ru.itis.study_manager.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.itis.study_manager.converter.ScheduleConverter;
import ru.itis.study_manager.converter.TaskConverter;
import ru.itis.study_manager.converter.UserEntityToDtoConverter;
import ru.itis.study_manager.converter.UserModelToEntityConverter;
import ru.itis.study_manager.dao.ScheduleDao;
import ru.itis.study_manager.dao.TaskDao;
import ru.itis.study_manager.dao.UserDao;
import ru.itis.study_manager.service.ScheduleService;
import ru.itis.study_manager.service.TaskService;
import ru.itis.study_manager.service.UserService;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserService userService = new UserService(
                new UserDao(), new UserModelToEntityConverter(), new UserEntityToDtoConverter()
        );
        sce.getServletContext().setAttribute("userService", userService);

        TaskService taskService = new TaskService(
                new TaskDao(), new TaskConverter()
        );
        sce.getServletContext().setAttribute("taskService", taskService);

        ScheduleService scheduleService = new ScheduleService(
                new ScheduleDao(), new ScheduleConverter()
        );
        sce.getServletContext().setAttribute("scheduleService", scheduleService);
    }
}
