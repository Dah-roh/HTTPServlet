<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 27/11/2023
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PRODUCT APPLICATION</title>
</head>
<body>
<h4>Log In</h4>
<span>
<form method="post" action="user">
    Email: <input name="email" type="email" placeholder="email">
    Password: <input name="password" type="password" placeholder="password">
    <input hidden="hidden" name="login" value="login">
    <button type="submit">Log in</button>
</form>
    </span>
</body>
</html>
