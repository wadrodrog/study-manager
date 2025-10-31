<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <link href="/favicon.ico" rel="icon">
        <link href="/static/css/style.css" rel="stylesheet">
        <c:forEach var="cssItem" items="${css}">
        <link href="/static/css/${cssItem}.css" rel="stylesheet">
        </c:forEach>
        <c:forEach var="jsItem" items="${js}">
        <script src="/static/js/${jsItem}.js"></script>
        </c:forEach>
    </head>