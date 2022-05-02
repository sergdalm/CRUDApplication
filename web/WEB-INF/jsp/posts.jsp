<%--
  Created by IntelliJ IDEA.
  User: Hello
  Date: 29.04.2022
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Posts</title>
</head>
<body>
    <h1>Posts of ${requestScope.writerName}</h1>
    <c:forEach var="post" items="${requestScope.posts}">
        <li>${post.title}</li>
    </c:forEach>

</body>
</html>
