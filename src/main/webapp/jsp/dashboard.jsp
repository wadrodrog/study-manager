<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<p>Привет, ${user.username}!</p>
<div>
    <p>
        До нового года осталось <span id="countdown"></span>
        <progress max="100" value="0" id="progress-bar"></progress>
        <span id="progress-text"></span>
    </p>
    <script src="/static/js/countdown.js"></script>
</div>
