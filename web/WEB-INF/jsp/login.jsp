<%--
  Created by IntelliJ IDEA.
  User: alexandria
  Date: 03/05/2022
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <form action="/login" method="post">
        <label for="email">Email:
            <input type="email" name="email" id="email">
        </label><br>
        <label for="password">Password:
            <input type="password" name="password" id="password">
        </label><br>
        <button type="submit">Login</button>
    </form>
</body>
</html>
