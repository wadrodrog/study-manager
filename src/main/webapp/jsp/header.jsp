<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <header>
        <nav>
            <ul>
                <c:choose>
                <c:when test="${authorized}">
                <li><a href="/dashboard">Обзор</a></li>
                <li><a href="/tasks">Задачи</a></li>
                <li><a href="/settings">Настройки</a></li>
                <li><a href="/logout">Выйти</a></li>
                </c:when>
                <c:otherwise>
                <li><a href="/welcome">Главная</a></li>
                <li><a href="/login">Войти</a></li>
                <li><a href="/register">Зарегистрироваться</a></li>
                </c:otherwise>
                </c:choose>
                <fieldset id="theme-switcher">
                    <label>
                        🌗
                        <input type="radio" id="theme-auto" name="theme" value="auto" onchange="saveTheme('auto')" <c:if test="${user.theme == 0}">checked</c:if> />
                    </label>
                    <label>
                        ☀️
                        <input type="radio" id="theme-light" name="theme" value="light" onchange="saveTheme('light')" <c:if test="${user.theme == 1}">checked</c:if> />
                    </label>
                    <label>
                        🌙
                        <input type="radio" id="theme-dark" name="theme" value="dark" onchange="saveTheme('dark')" <c:if test="${user.theme == 2}">checked</c:if> />
                    </label>
                </fieldset>
            </ul>
        </nav>
    </header>
