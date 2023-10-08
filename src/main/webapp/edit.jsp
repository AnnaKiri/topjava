<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>${meal.id == MealsUtil.UNKNOWN_ID ? 'Add meal' : 'Edit meal'}</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="meals">Meals</a></h3>
<hr>
<h2>${meal.id == MealsUtil.UNKNOWN_ID ? 'Add meal' : 'Edit meal'}</h2>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Date/Time:</dt>
        <input type="datetime-local" name="dateTime" value="${meal.dateTime}">
    </dl>
    <dl>
        <dt>Description:</dt>
        <input type="text" name="description" value="${meal.description}">
    </dl>
    <dl>
        <dt>Calories:</dt>
        <input type="text" name="calories" value="${meal.calories}">
    </dl>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cansel</button>
</form>
</body>
</html>
