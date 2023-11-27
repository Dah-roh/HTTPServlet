<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <style type="text/css" >
        .main-sign-up {
            display: block;
        }
    </style>
    <title>SAMPLE HTTP APPLICATION</title>
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
Already have an account? <a href="user">Log in</a>
</span>
</body>
</html>