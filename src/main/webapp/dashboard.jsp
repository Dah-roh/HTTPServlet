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

<h1>
    <%  PrintWriter out1 = response.getWriter();

        out1.println("<h2>Product Dashboard</h2>" +
                "<table>\n" +
                "  <thead> <tr><th>Name </th> <th> Price </th><th>Quantity</th><th> Buy </th></tr></thead>");
        List<Product> productList = (List<Product>) request.getAttribute("product-list");
        productList.forEach(product -> {
            out1.println(
                    "<tr><td>"+
                            product.getName()+" "+ "</td><td>" +
                            product.getProductPrice()+""+ "</td><td>"+
                            "Quant. <input name="+"quantity value=0>"+ "</td><td>"
            );

            out1.println("</td></tr>");
        });
        out1.println("\n" +
                "</table>");
        out1.println("<a href ='product?buy="+"'> Buy</a>");
    %>
</h1>
</body>
</html>
