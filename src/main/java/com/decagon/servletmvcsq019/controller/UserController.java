package com.decagon.servletmvcsq019.controller;

import com.decagon.servletmvcsq019.dao.ProductDao;
import com.decagon.servletmvcsq019.dao.UserDao;
import com.decagon.servletmvcsq019.serviceImpl.UserServiceImpl;
import com.decagon.servletmvcsq019.dto.LoginRequestDto;
import com.decagon.servletmvcsq019.dto.UserDto;
import com.decagon.servletmvcsq019.models.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

@WebServlet(name ="user", value = "/user")
public class UserController extends HttpServlet {

    private Logger logger = Logger.getGlobal();
    //GET REQUEST ARE HANDLED BELOW
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login")==null?null:req.getParameter("login");//if not NULL line 35 happens...
        String admin = req.getParameter("admin");//if this request parameter has a value, line 31 happens...
        if (admin!=null&& Objects.equals(admin, "true")){//...it means the admin wants to go to admin page to add or edit products
            RequestDispatcher dispatcher = req.getRequestDispatcher("admin.jsp");
            dispatcher.forward(req, resp);
        }
        if (login!=null){//...this means the user requested to log in. So we redirect the user to the login.jsp page
            RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req, resp);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("sign-up.jsp");//if both parameters are NULL, then the application just loaded and the user has clicked on the sign up button...
        dispatcher.forward(req, resp);
    }
    //POST REQUESTS ARE HANDLED BELOW
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserServiceImpl userService = new UserServiceImpl();
        UserDao userDao = new UserDao();
        String login = req.getParameter("login")==null?null:req.getParameter("login");//if this request parameter is not NULL then the user has inputted their credentials to login...
        if (login!=null){//...and the login function inside this condition is carried out
            LoginRequestDto loggedInUser = new LoginRequestDto(req.getParameter("email"), req.getParameter("password"));//We get the email and password they inputted, and we use it...
//            Users user = userService.findUserByEmail.apply(loggedInUser);
            Users user =  userDao.findUser.apply(loggedInUser);//...to find the user, once we get the user...
            loggedInUser.setHashPassword(user.getPassword());//...we get their hashpassword and verify the password inputted is...
            if (userService.verifyPassword.apply(loggedInUser)){//...correct, and...
                HttpSession session = req.getSession();
                session.setAttribute("userID", user.getId());//...store their id in a session for tracking the user activity and personalizing their response.
                req.setAttribute("product-list", new ProductDao().findAllProducts.get());//Find all the products in the database to display in the...
                RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");//...dashboard page for the user.
                dispatcher.forward(req, resp);
            }
            else{//...inputted password is incorrect
                RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");//...we take them back to the index page so they either sign up or login again.
                dispatcher.forward(req, resp);
            }
        }
       UserDto userDto = new UserDto();
       userDto.setName(req.getParameter("username"));
       userDto.setPassword(req.getParameter("password"));
       userDto.setEmail(req.getParameter("email"));
//       logger.info("SAVED USER --> "+userService.saveUserInformation.apply(userDto));
        logger.info("SAVED USER --> "+userDao.saveUser.apply(new Users(userDto)));//if line 47 is NULL then the client request is not meant for the login feature but the SignUp feature
        RequestDispatcher dispatcher = req.getRequestDispatcher("successful.jsp");//once successfully signed up, we show them a success page.
        dispatcher.forward(req, resp);

    }
}
