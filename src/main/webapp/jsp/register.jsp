<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <form method="post" action="/register">
            <div>
                <label>
                    Имя пользователя:
                    <input type="username" name="username" maxlength="255" pattern="^[a-zA-Z0-9_]{1,255}$" required />
                </label>
            </div>
            <div>
                <label>
                    Пароль:
                    <input type="password" name="password" minlength="8" maxlength="255" required />
                </label>
            </div>
            <div>
                <label>
                    Повторите пароль:
                   <input type="password" name="repeat_password" minlength="8" maxlength="255" required />
                </label>
            </div>
            <div>
                <input type="submit" value="Зарегистрироваться">
            </div>
        </form>
        <p class="error">
            <c:choose>
            <c:when test="${param.error == 'Passwords do not match'}">
            Пароли не совпадают.
            </c:when>
            <c:when test="${param.error == 'Username already exists'}">
            Пользователь с таким именем уже существует.
            </c:when>
            <c:otherwise>
            ${param.error}
            </c:otherwise>
            </c:choose>
        </p>
