<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.decagon.servletmvcsq019.models.Product" %>
<%@ page import="java.util.List" %><%--
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
<% HttpSession session1 = request.getSession();
session1.setAttribute("userID", session.getAttribute("userID"));%>
<h1>
    <%  PrintWriter out1 = response.getWriter();

        out1.println("<h2>Product Dashboard</h2>" +
                "<table>\n" +
                "  <thead> <tr><th>Name </th> <th> Price </th><th> Buy </th></tr></thead>");
        //THE LINE BELOW IS RESPONSIBLE FOR DISPLAYING THE PRODUCT LIST IN LINE 123,129... AND ANYWHERE ELSE THAT STORES THE DATABASE PRODUCT LIST IN
        //THE REQUEST ATTRIBUTE WITH THE NAME "product-list"
        List<Product> productList = (List<Product>) request.getAttribute("product-list");
        productList.forEach(product -> {
            out1.println(
                    "<tr><td>"+
                            product.getName()+"</td><td>" +
                            product.getProductPrice()+"</td><td>"+
                            //the href below is responsible for making line 41 in the product controller NOT NULL
                            "<a href ='product?buy="+product.getId()+"'> Buy</a>"+
                            "</td></tr>"
            );
        });
        out1.println("\n" +
                "</table>");
        out1.println();
    %>
<%--    the href below is responsible for making line 36 in the product controller NOT NULL--%>
    <a href="product?payment=1">Proceed to payment</a>
</h1>
</body>
</html>
