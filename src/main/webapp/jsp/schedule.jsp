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
        <p>Сегодня <span id="datetime-display"></span></p>
        <table>
            <thead>
                <tr>
                    <th>Время</th>
                    <th>Название</th>
                    <th>Место</th>
                    <th>Примечание</th>
                    <th>Действие</th>
                </tr>
            </thead>
            <tbody>
                <c:set var="weekday" value="0" scope="page"/>
                <c:forEach var="event" items="${schedule}">
                <c:if test="${weekday != event.weekday}">
                <c:set var="weekday" value="${event.weekday}" scope="page"/>
                <tr>
                    <td class="weekday" colspan="5">
                    <c:choose>
                    <c:when test="${weekday == 1}">Понедельник</c:when>
                    <c:when test="${weekday == 2}">Вторник</c:when>
                    <c:when test="${weekday == 3}">Среда</c:when>
                    <c:when test="${weekday == 4}">Четверг</c:when>
                    <c:when test="${weekday == 5}">Пятница</c:when>
                    <c:when test="${weekday == 6}">Суббота</c:when>
                    <c:when test="${weekday == 7}">Воскресенье</c:when>
                    </c:choose>
                    </td>
                </tr>
                </c:if>
                <div class="event">
                    <tr id="event-${event.eventId}">
                        <td class="time">
                            <span class="contents">${event.timeStart.toString().substring(0, 5)}–${event.timeEnd.toString().substring(0, 5)}</span>
                            <span class="edit" style="display: none;">
                                <input class="start" type="time" />–<input class="end" type="time" />
                            </span>
                        </td>
                        <td class="name">
                            <span class="contents">${event.name}</span>
                            <input class="edit" style="display: none;" type="text" placeholder="Название" pattern="(.|\s)*\S(.|\s)*" minlength="1" maxlength="256" />
                        </td>
                        <td class="place">
                            <span class="contents">${event.place}</span>
                            <input class="edit" style="display: none;" type="text" placeholder="Место" pattern="(.|\s)*\S(.|\s)*" maxlength="256" />
                        </td>
                        <td class="notes">
                            <span class="contents">${event.notes}</span>
                            <input class="edit" style="display: none;" type="text" placeholder="Примечание" pattern="(.|\s)*\S(.|\s)*" maxlength="256" />
                        </td>
                        <td class="actions">
                            <select name="weekday" class="weekday" style="display: none;">
                                <option value="1" <c:if test="${event.weekday == 1}">selected</c:if>>Понедельник</option>
                                <option value="2" <c:if test="${event.weekday == 2}">selected</c:if>>Вторник</option>
                                <option value="3" <c:if test="${event.weekday == 3}">selected</c:if>>Среда</option>
                                <option value="4" <c:if test="${event.weekday == 4}">selected</c:if>>Четверг</option>
                                <option value="5" <c:if test="${event.weekday == 5}">selected</c:if>>Пятница</option>
                                <option value="6" <c:if test="${event.weekday == 6}">selected</c:if>>Суббота</option>
                                <option value="7" <c:if test="${event.weekday == 7}">selected</c:if>>Воскресенье</option>
                            </select>
                            <button class="edit" onclick="editEvent(${event.eventId})" title="Изменить">🖍️</button>
                            <button class="save" onclick="saveEvent(${event.eventId})" title="Сохранить" style="display: none;">✅</button>
                            <button onclick="deleteEvent(${event.eventId})" title="Удалить">🗑️</button>
                        </td>
                    </tr>
                </div>
                </c:forEach>
            </tbody>
        </table>
