<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css" >
        .main-sign-up {
            display: block;
        }
    </style>
    <title>PRODUCT APPLICATION</title>
</head>
<body>
<h1>
    <% String hello = "Hello World!";
    System.out.println(hello);
    %>


    <%= hello%>

</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<span>
<a class="main-sign-up" href="user">Sign up</a>
    <%-- the href below is responsible for making line 29 in the UserController NOT NULL--%>
Already have an account? <a href="user?login=login">Log in</a> <br>
<%-- the href below is responsible for making line 30 in the UserController NOT NULL--%>
    <a href="user?admin=true">Admin page</a>
</span>
</body>
</html>