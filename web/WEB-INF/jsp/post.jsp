<%--
  Created by IntelliJ IDEA.
  User: alexandria
  Date: 16/05/2022
  Time: 19:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>${requestScope.post.title}</title>
</head>
<body>
    <div>Author: ${requestScope.writer.fullName}</div>
    <div>${requestScope.post.content}</div>
    <div>Labels:
        <c:forEach var="lable" items="${requestScope.labels}">
            ${lable.name}
        </c:forEach>
    </div>
    <div>Created: ${requestScope.post.created}</div>
    <c:if test="${not empty requestScope.post.updated}">
        <div>Updated: ${requestScope.post.updated}</div>
    </c:if>
</body>
</html>
