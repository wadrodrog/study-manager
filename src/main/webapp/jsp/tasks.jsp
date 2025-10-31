<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <details>
            <summary>Новая задача</summary>
            <form method="post" action="/tasks">
                <div>
                    <label>
                        Заголовок:
                        <input type="text" name="title" required />
                    </label>
                </div>
                <div>
                    <label>
                        Дедлайн:
                        <input type="date" name="due" />
                    </label>
                </div>
                <div>
                    <label>
                        Содержимое:
                        <textarea name="contents" rows="5" cols="33"></textarea>
                    </label>
                </div>
                <div>
                    <input type="submit" value="Создать">
                </div>
            </form>
        </details>
        <c:forEach var="task" items="${tasks}">
        <div id="task-${task.taskId}" class="task">
            <h2>${task.title}</h2>
            <p>${task.contents}</p>
            <label>
                Дедлайн:
                <input type="date" name="due" value="${task.due}" onchange="updateDue(${task.taskId})" />
            </label>
            <select name="status" onchange="updateStatus(${task.taskId})">
                <option value="Incomplete" <c:if test="${task.status == 'INCOMPLETE'}">selected</c:if>>Не завершено</option>
                <option value="In progress" <c:if test="${task.status == 'IN_PROGRESS'}">selected</c:if>>В процессе</option>
                <option value="Complete" <c:if test="${task.status == 'COMPLETE'}">selected</c:if>>Завершено</option>
            </select>
            <button onclick="deleteTask(${task.taskId})">Удалить</button>
        </div>
        </c:forEach>