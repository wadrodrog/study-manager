<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <p>Привет, ${user.username}!</p>
        <p>Сегодня <span id="datetime-display"></span></p>
        <table>
            <thead>
                <tr>
                    <th>Время</th>
                    <th>Название</th>
                    <th>Место</th>
                    <th>Примечание</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="event" items="${schedule}">
                <tr>
                    <td>${event.timeStart.toString().substring(0, 5)}–${event.timeEnd.toString().substring(0, 5)}</td>
                    <td>${event.name}</td>
                    <td>${event.place}</td>
                    <td>${event.notes}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <h2>Ближайшие задачи</h2>
        <c:forEach var="task" items="${tasks}">
        <div class="task">
            <div class="title">
                <div class="display">
                    <h2>${task.title}</h2>
                </div>
            </div>
            <div class="contents">
                <div class="display">
                    <p>${task.contents}</p>
                </div>
            </div>
            <p>
                Приоритет: ${task.priority}, Дедлайн: ${task.due}, Статус: ${task.status.getRu()}
            </p>
        </div>
        </c:forEach>
