<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexandria
  Date: 16/05/2022
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
        <label for="firstName"> First name:
            <input type="text" name="firstName" id="firstName">
        </label><br>
        <label for="lastName"> Last name:
            <input type="text" name="lastName" id="lastName">
        </label><br>
        <label for="email"> Email:
            <input type="email" name="email" id="email">
        </label><br>
        <label for="password"> Password:
          <input type="password" name="password" id="password">
        </label><br>
        <label for="checkPassword"> Repeat password:
          <input type="password" name="checkPassword" id="checkPassword">
        </label><br>
        <button type="submit">Submit</button>
        <c:if test="${not empty requestScope.errors}">
            <div style="color: red">
                <c:forEach var="error" items="${requestScope.errors}">
                    <span>${error.message}</span>
                    <br>
                </c:forEach>
            </div>
        </c:if>
    </form>
</body>
</html>
