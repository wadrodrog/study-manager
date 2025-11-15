<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="/favicon.ico" rel="icon">
        <link href="/static/css/style.css" rel="stylesheet">
        <c:forEach var="cssItem" items="${css}">
        <link href="/static/css/${cssItem}.css" rel="stylesheet">
        </c:forEach>
        <c:if test="${theme == 1}">
        <link href="/static/css/theme/light.css" rel="stylesheet">
        </c:if>
        <c:if test="${theme == 2}">
        <link href="/static/css/theme/dark.css" rel="stylesheet">
        </c:if>
        <c:forEach var="jsItem" items="${js}">
        <script src="/static/js/${jsItem}.js"></script>
        </c:forEach>
    </head>