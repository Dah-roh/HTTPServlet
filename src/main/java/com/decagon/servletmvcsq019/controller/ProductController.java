package com.decagon.servletmvcsq019.controller;

import com.decagon.servletmvcsq019.dao.OrderDao;
import com.decagon.servletmvcsq019.dao.ProductDao;
import com.decagon.servletmvcsq019.dao.UserDao;
import com.decagon.servletmvcsq019.models.Cart;
import com.decagon.servletmvcsq019.models.Order;
import com.decagon.servletmvcsq019.models.Product;
import com.decagon.servletmvcsq019.models.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "product", value = "/product")
public class ProductController extends HttpServlet {
    /*
    This is the Product Controller/Servlet class that is responsible for handling clients request that relate to the product entity in
    our ecommerce system.
     */
    //GET REQUESTS ARE HANDLED BELOW
    private ProductDao productDao = new ProductDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //The next five lines are responsible for retrieving request parameter that will be used to create conditions necessary
        //to determine our application's product functionality. If any of these request parameters is not NULL then a condition that holds the logic for functionality becomes true, and the feature
        // is carried out.
        String viewProduct = req.getParameter("admin");//this admin request parameter actives the logic in the condition at line 121 to enable an admin view all products

        String editProduct = req.getParameter("edit");//it enables the condition in line 113 below to happen when it is not NULL

        String deleteProduct = req.getParameter("delete");//NOT USED AT THE MOMENT...
        String buyproduct = req.getParameter("buy");//it enables the logic in the conditional statement at line 92...

        String payment = req.getParameter("payment");//it enables the logic in line 72's conditional statment to run when user wishes to make payment

        Cart cart = new Cart();
        Order makeOrderPayment = (Order) req.getSession().getAttribute("order");//gets the session that stores the user's order in line 87 below, so they(the user) can make payment
        Cart paymentCart = (Cart) req.getSession().getAttribute("cart");//gets the session for holding cart details awaiting when the user intends to make an order.

        if (makeOrderPayment!=null){//the conditional statement responsible for handling user's payment for an order
            UserDao userDao = new UserDao();
            OrderDao orderDao = new OrderDao();
            Long id = (Long) req.getSession().getAttribute("userID");//gets the stored userID session created upon user login
            Users user = userDao.findUserById.apply(id);//with the id we can get user object from the Database
            if (user.getBalance().doubleValue()>makeOrderPayment.getTotalPrice().doubleValue()){//if the user's balance is more than the order's total price...
                BigDecimal balance = user.getBalance().subtract(makeOrderPayment.getTotalPrice());//subtract the order's total price from the user's balance and ...
                System.out.println(balance);
                user.setBalance(balance);
                userDao.updateUserBalance.apply(user);//...update the user's balance in the database with the new balance after payment
                orderDao.saveOrder.apply(makeOrderPayment);//save the order made
                req.setAttribute("paid", "Payment made successfully, your product will be delivered shortly!");//since payment was successful they get this message, otherwise see line 66 & 67 below happens...
                req.getSession().setAttribute("order", null);//remove the order from the order session, so that we have a fresh order session to work with upon the next order
                RequestDispatcher dispatcher = req.getRequestDispatcher("payment-successful.jsp");//respond to their payment request with the payment-successful jsp page
                dispatcher.forward(req, resp);
            }
            else {
                req.getSession().setAttribute("order", null);//...their balance is therefore lesser than their order, and we clear the order from the session...
                req.setAttribute("paid", "Insufficient Balance in your account!");//...and then, they get this message...
                RequestDispatcher dispatcher = req.getRequestDispatcher("payment-successful.jsp");//...in the payment successful jsp page as their response.
                dispatcher.forward(req, resp);
            }
        }
        if (payment!=null&&paymentCart!=null){//this is condition that holds the feature for summarising the cart items total price before payment is made. It is activated if line 43 is not NULL
            Order order = new Order();
            final BigDecimal[] totalPrice = {new BigDecimal(0)};
            List<Product> productList = new ArrayList<>();
            paymentCart.getProductIds().forEach(id->{
                order.setProductIds((order.getProductIds() != null ? order.getProductIds()+"," : "") + id);//product ids are stored as a csv string in the database which (if need be) we can
                //use the string split method to convert it into an arrayList of ids.
                Product product = productDao.findProductById.apply(id);
                //to prevent multithreading problems primitive array is used here is primitive datatype are thread safe
                totalPrice[0] = totalPrice[0].add(product.getProductPrice());//adds price of each product in our cart
                productList.add(product);//creates a list of products that user intends to buy and...(see line 88) where the product list is sent as a response to the user, so they are aware
                //the items they wish to order.
            });
            HttpSession orderSession = req.getSession();
            order.setTotalPrice(totalPrice[0]);
            orderSession.setAttribute("order", order);//stores our user's order in a session
            req.setAttribute("product-list", productList);//sends a request attribute containing the user's product(s) order.
            RequestDispatcher dispatcher = req.getRequestDispatcher("payment.jsp");
            dispatcher.forward(req, resp);
        }
        if (buyproduct!=null){//condition that handles the add to cart or buy feature...
            if (req.getSession().getAttribute("cart")!=null){//if there is already a cart session...
                cart = (Cart) req.getSession().getAttribute("cart");//...get the session
                cart.getProductIds().add(Long.parseLong(buyproduct));//...add the product id of the newly selected product that the user wishes to buy
            }
            else {
                HttpSession cartSession = req.getSession();
                Long id = (Long) cartSession.getAttribute("userID");
                cart.setUserId(id);//..if no cart session exists, create a cart object and get user id from the "userID" session and store it in the cart object
                List<Long> productIds = new ArrayList<>();//create an arraylist for storing the product id of products in the cart
                productIds.add(Long.parseLong(buyproduct));//add product id to arrayList
                cart.setProductIds(productIds);//set product ids arrayList in the cart object
                cartSession.setAttribute("cart", cart);//create a new cart session and store the cart object in it.
            }
            System.out.println(cart);
            List<Product> productList = productDao.findAllProducts.get();
            req.setAttribute("product-list", productList);//get all the database products and take the user back to the same page (dashboard.jsp) they were when they selected their product(almost as if they
            //never left the page) so they can continue adding to cart
            RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(req, resp);
        }
        if(editProduct!=null){//it is responsible for getting all information relating to the product about to be edited...
            Product product =  productDao.findProductById.apply(Long.parseLong(editProduct));
            req.setAttribute("edit-product", product);//...setting the product information, and...
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");//...and sending it to admin.jsp
            dispatcher.forward(req, resp);
        }

        //line 121 to 137 all do the same thing but from different client request.
        if (viewProduct!=null){//from the admin.jsp page to view all products
            List<Product> productList = productDao.findAllProducts.get();
            req.setAttribute("product-list", productList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin-view-product.jsp");
            dispatcher.forward(req, resp);
        }
        if (req.getAttribute("shop")!=null) {//from the payment-successful.jsp page to view all product
            List<Product> productList = productDao.findAllProducts.get();
            req.setAttribute("product-list", productList);
            RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(req, resp);
        }
        List<Product> productList = productDao.findAllProducts.get();//from the login.jsp page
        req.setAttribute("product-list", productList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(req, resp);
    }
    //POST REQUESTS ARE HANDLED BELOW
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adminAddProduct = req.getParameter("admin");//this request parameter comes from the admin.jsp to enable adding product feature in line 153 below
        String editProduct = req.getParameter("edit");//this request parameter comes from the admin-view-product.jsp, and it holds the id of the product the admin wishes to edit...
        String id = req.getParameter("id");
        if (editProduct!=null){  String productName = req.getParameter("product-name");//
            String productPrice = req.getParameter("product-price");
            String productQuantity = req.getParameter("product-quantity");
            String productCategory = req.getParameter("product-category");
            Product product = new Product(productName, productCategory, Long.parseLong(productQuantity), new BigDecimal(productPrice));
            product.setId(Long.parseLong(id));
            productDao.updateProduct.apply(product);//...this enables us update the product having that id
            resp.sendRedirect("product?admin=view-product");
        }
        if (adminAddProduct!=null){//saves a product to our MySQL database
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
