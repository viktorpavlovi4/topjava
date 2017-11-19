<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            color: green
        }
        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr/>
<h2>Meals</h2>
<table border="1" cellpadding="1" cellspacing="0">
    <thead>
    <tr>
        <th>Date&Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>
    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
        <td>
            <%--<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />--%>
            <%--<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${meal.dateTime}" />--%>
            ${fn:formatDateTime(meal.dateTime)}
        </td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
        <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
        <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<hr/>
<a href="meals?action=create">Add meal</a>
</body>
</html>
