<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <form method="post" action="/login">
            <div>
                <label>
                    Имя пользователя:<br>
                    <input type="username" name="username" maxlength="255" pattern="^[a-zA-Z0-9_]{1,32}$" required />
                </label>
            </div>
            <div>
                <label>
                    Пароль:<br>
                    <input type="password" name="password" minlength="8" maxlength="255" required />
                </label>
            </div>
            <div>
                <input type="submit" value="Войти">
            </div>
        </form>
        <p class="error">
            <c:choose>
            <c:when test="${param.error == null}"></c:when>
            <c:when test="${param.error == 'Invalid credentials'}">
            Неверное имя пользователя или пароль.
            </c:when>
            <c:otherwise>Не удалось войти в аккаунт.</c:otherwise>
            </c:choose>
        </p>