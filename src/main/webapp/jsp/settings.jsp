<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <p class="error">
            <c:choose>
            <c:when test="${param.error == null}"></c:when>
            <c:when test="${param.error == 'Wrong password'}">
            Неверный пароль.
            </c:when>
            <c:otherwise>Не удалось обновить настройки.</c:otherwise>
            </c:choose>
        </p>
        <p class="success">
            <c:choose>
            <c:when test="${param.success == null}"></c:when>
            <c:otherwise>Настройки обновлены.</c:otherwise>
            </c:choose>
        </p>
        Тема:
        <select name="theme" id="theme" oninput="updateTheme();">
            <option value="system" <c:if test="${theme == 0}">selected</c:if>>Системная</option>
            <option value="light" <c:if test="${theme == 1}">selected</c:if>>Светлая</option>
            <option value="dark" <c:if test="${theme == 2}">selected</c:if>>Тёмная</option>
        </select>
        <fieldset>
            <legend>Имя пользователя</legend>
            <form method="post" action="/settings">
                <div>
                    <label>
                        Текущий пароль:<br>
                        <input type="password" name="current_password" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <label>
                        Новое имя пользователя:<br>
                        <input type="username" name="new_username" maxlength="255" pattern="^[a-zA-Z0-9_]{1,32}$" required />
                    </label>
                </div>
                <div>
                    <input type="submit" value="Изменить имя пользователя">
                </div>
            </form>
        </fieldset>
        <br>
        <fieldset>
            <legend>Пароль</legend>
            <form method="post" action="/settings">
                <div>
                    <label>
                        Текущий пароль:<br>
                        <input type="password" name="current_password" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <label>
                        Новый пароль:<br>
                        <input type="password" name="new_password" id="password" oninput="check();" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <label>
                        Повторите пароль:<br>
                        <input type="password" name="repeat_password" id="repeat-password" oninput="check();" minlength="8" maxlength="255" required />
                    </label>
                </div>
                <div>
                    <input type="submit" id="submit" value="Изменить пароль">
                </div>
            </form>
        </fieldset>