<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <form method="post" action="/register">
            <div>
                <label>
                    Имя пользователя:<br>
                    <input type="username" name="username" maxlength="255" pattern="^[a-zA-Z0-9_]{1,255}$" required />
                </label>
            </div>
            <div>
                <label>
                    Пароль:<br>
                    <input type="password" name="password" id="password" oninput="check();" minlength="8" maxlength="255" required />
                </label>
            </div>
            <div>
                <label>
                    Повторите пароль:<br>
                   <input type="password" id="repeat-password" oninput="check();" minlength="8" maxlength="255" required />
                </label>
            </div>
            <div>
                <input type="submit" id="submit" value="Зарегистрироваться" disabled>
            </div>
        </form>
        <p class="error">
            <c:choose>
            <c:when test="${param.error == null}"></c:when>
            <c:when test="${param.error == 'User already exists'}">
            Пользователь с таким именем уже существует.
            </c:when>
            <c:otherwise>Не удалось зарегистрироваться.</c:otherwise>
            </c:choose>
        </p>