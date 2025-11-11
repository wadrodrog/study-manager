<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <details>
            <summary>Новая задача</summary>
            <form method="post" action="/tasks">
                <div>
                    <input type="text" name="title" placeholder="Заголовок" pattern="(.|\s)*\S(.|\s)*" maxlength="256" required />
                </div>
                <div>
                    <textarea name="contents" rows="5" cols="33" placeholder="Сделать домашку..." maxlength="10000"></textarea>
                </div>
                <div>
                    <label>
                        Дедлайн:
                        <input type="date" name="due" />
                    </label>
                </div>
                <div>
                    <label>
                        Приоритет:
                        <input type="number" name="priority" min="-100" max="100" value="0" />
                    </label>
                </div>
                <div>
                    <label>
                        Статус:
                        <select name="status">
                            <option value="incomplete" selected>Не завершено</option>
                            <option value="in_progress">В процессе</option>
                            <option value="complete">Завершено</option>
                        </select>
                    </label>
                </div>
                <div>
                    <input type="submit" value="Создать">
                </div>
            </form>
        </details>
        <nav class="view-control">
            <span>Всего задач: ${totalCount}</span>
            <div class="pager">
                <c:if test="${page > 1}"><a href="/tasks?page=${page - 1}">⬅️</a></c:if>
                ${page}/${maxPage}
                <c:if test="${page < maxPage}"><a href="/tasks?page=${page + 1}">➡️</a></c:if>
            </div>
            <select name="sort" id="sort" oninput="updateSort();">
                <option value="created_at" <c:if test="${param.sort == 'created_at'}">selected</c:if>>По дате создания</option>
                <option value="title" <c:if test="${param.sort == 'title'}">selected</c:if>>По заголовку</option>
                <option value="priority" <c:if test="${param.sort == 'priority'}">selected</c:if>>По приоритету</option>
                <option value="due" <c:if test="${param.sort == 'due'}">selected</c:if>>По дедлайну</option>
            </select>
            <label>
                По убыванию
                <input type="checkbox" id="descending" oninput="updateSort();" <c:if test="${param.descending != null}">checked</c:if>>
            </label>
        </nav>
        <c:forEach var="task" items="${tasks}">
        <div id="task-${task.taskId}" class="task">
            <div class="title">
                <div class="editor inactive">
                    <input type="text" oninput="checkTitle(${task.taskId});" maxlength="256" placeholder="Заголовок задачи">
                    <button onclick="saveTitle(${task.taskId});">✅</button>
                </div>
                <div class="display">
                    <h2>${task.title}</h2>
                    <button class="edit" title="Редактировать заголовок" onclick="editTitle(${task.taskId});">🖍️</button>
                </div>
            </div>
            <div class="contents">
                <div class="editor inactive">
                    <textarea rows="5" cols="33" placeholder="Сделать домашку..." maxlength="10000"></textarea>
                    <button onclick="saveContents(${task.taskId});">✅</button>
                </div>
                <div class="display">
                    <p>${task.contents}</p>
                    <button class="edit" title="Редактировать содержимое" onclick="editContents(${task.taskId});">🖍️</button>
                </div>
            </div>
            <label>
                Приоритет:
                <input type="number" name="priority" min="-100" max="100" value="${task.priority}" onchange="updatePriority(${task.taskId})" />
            </label>
            <label>
                Дедлайн:
                <input type="date" name="due" value="${task.due}" onchange="updateDue(${task.taskId})" />
            </label>
            <select name="status" onchange="updateStatus(${task.taskId})">
                <option value="incomplete" <c:if test="${task.status == 'INCOMPLETE'}">selected</c:if>>Не завершено</option>
                <option value="in_progress" <c:if test="${task.status == 'IN_PROGRESS'}">selected</c:if>>В процессе</option>
                <option value="complete" <c:if test="${task.status == 'COMPLETE'}">selected</c:if>>Завершено</option>
            </select>
            <button onclick="deleteTask(${task.taskId})">Удалить</button>
        </div>
        </c:forEach>