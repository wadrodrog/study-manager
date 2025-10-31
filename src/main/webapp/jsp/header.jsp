<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <header>
        <nav>
            <ul>
                <li><a href="/welcome">Главная</a></li>
                <c:choose>
                <c:when test="${authorized}">
                <li><a href="/tasks">Задачи</a></li>
                <li><a href="/logout">Выйти</a></li>
                </c:when>
                <c:otherwise>
                <li><a href="/login">Войти</a></li>
                <li><a href="/register">Зарегистрироваться</a></li>
                </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </header>
