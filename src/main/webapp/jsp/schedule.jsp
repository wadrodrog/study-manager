<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <p class="error">
            <c:choose>
            <c:when test="${param.error == null}"></c:when>
            <c:when test="${param.error == 'Invalid time range'}">
            Время окончания не может быть раньше времени начала.
            </c:when>
            <c:otherwise>Не удалось создать событие.</c:otherwise>
            </c:choose>
        </p>
        <details>
            <summary>Новое событие</summary>
            <form method="post" action="/schedule">
                <div>
                    <label>
                        День недели:
                        <select name="weekday">
                            <option value="1">Понедельник</option>
                            <option value="2">Вторник</option>
                            <option value="3">Среда</option>
                            <option value="4">Четверг</option>
                            <option value="5">Пятница</option>
                            <option value="6">Суббота</option>
                            <option value="7">Воскресенье</option>
                        </select>
                    </label>
                </div>
                <div>
                    <input type="text" name="name" placeholder="Название" pattern="(.|\s)*\S(.|\s)*" maxlength="256" required />
                </div>
                <div>
                    <input type="text" name="place" placeholder="Место" pattern="(.|\s)*\S(.|\s)*" maxlength="256" />
                </div>
                <div>
                    <input type="text" name="notes" placeholder="Примечание" pattern="(.|\s)*\S(.|\s)*" maxlength="256" />
                </div>
                <div>
                    <label>
                        Начало:
                        <input type="time" name="time_start" />
                    </label>
                </div>
                <div>
                    <label>
                        Конец:
                        <input type="time" name="time_end" />
                    </label>
                </div>
                <div>
                    <input type="submit" value="Создать">
                </div>
            </form>
        </details>
    <h2>Понедельник</h2>
    <h2>Вторник</h2>
    <h2>Среда</h2>
    <h2>Четверг</h2>
    <h2>Пятница</h2>
    <h2>Суббота</h2>
    <h2>Воскресенье</h2>
