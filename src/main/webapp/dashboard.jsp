<%@ page import="java.io.PrintWriter" %><%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 28/11/2023
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PRODUCT APPLICATION</title>
</head>
<body>
<h1>
<%  PrintWriter out1 = response.getWriter();
    out1.println("<html><body>");
    out1.println("<h1>" + request.getAttribute("userId")+ "</h1>");
    out1.println("</body></html>");
%>
</h1>
<h2>Product Dashboard</h2>
</body>
</html>
