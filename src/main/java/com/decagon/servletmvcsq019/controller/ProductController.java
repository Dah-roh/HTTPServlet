package com.decagon.servletmvcsq019.controller;

import com.decagon.servletmvcsq019.dao.ProductDao;
import com.decagon.servletmvcsq019.models.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "product", value = "/product")
public class ProductController extends HttpServlet {

    private ProductDao productDao = new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewProduct = req.getParameter("admin");
        String editProduct = req.getParameter("edit");
        String deleteProduct = req.getParameter("delete");
        if(editProduct!=null){
            Product product =  productDao.findProductById.apply(Long.parseLong(editProduct));
            req.setAttribute("edit-product", product);
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
            dispatcher.forward(req, resp);
        }
        if (deleteProduct!=null){

        }
        if (viewProduct!=null){
            List<Product> productList = productDao.findAllProducts.get();
            req.setAttribute("product-list", productList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin-view-product.jsp");
            dispatcher.forward(req, resp);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminAddProduct = req.getParameter("admin");
        String editProduct = req.getParameter("edit");
        String id = req.getParameter("id");
        if (editProduct!=null){  String productName = req.getParameter("product-name");
            String productPrice = req.getParameter("product-price");
            String productQuantity = req.getParameter("product-quantity");
            String productCategory = req.getParameter("product-category");
            Product product = new Product(productName, productCategory, Long.parseLong(productQuantity), new BigDecimal(productPrice));
            product.setId(Long.parseLong(id));
            productDao.updateProduct.apply(product);
            resp.sendRedirect("product?admin=view-product");
        }
        if (adminAddProduct!=null){
            String productName = req.getParameter("product-name");
            String productPrice = req.getParameter("product-price");
            String productQuantity = req.getParameter("product-quantity");
            String productCategory = req.getParameter("product-category");
            Product product = new Product(productName, productCategory, Long.parseLong(productQuantity), new BigDecimal(productPrice));
            if (!productDao.saveProduct.apply(product)){
                List<Product> productList = productDao.findAllProducts.get();
                req.setAttribute("product-list", productList);
                RequestDispatcher dispatcher = req.getRequestDispatcher("admin-view-product.jsp");
                dispatcher.forward(req, resp);
            }
            else{
                req.setAttribute("error", "Could not add product");
                RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
                dispatcher.forward(req, resp);
            }
        }
    }
}
