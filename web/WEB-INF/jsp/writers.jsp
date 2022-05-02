<%--
  Created by IntelliJ IDEA.
  User: Hello
  Date: 29.04.2022
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Writers</title>
</head>
<body>
    <%@include file="header.jsp"%>
    <h1>Writers!!</h1>
    <ul>
        <c:forEach var="writer" items="${requestScope.writers}">
            <li><a href="${pageContext.request.contextPath}/posts?writerId=${writer.id}">${writer.fullName}</a></li>
        </c:forEach>

    </ul>
<%--    <%@include file="footer.jsp"%>--%>
</body>
</html>
