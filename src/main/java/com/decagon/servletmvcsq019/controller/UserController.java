package com.decagon.servletmvcsq019.controller;

import com.decagon.servletmvcsq019.UserServiceImpl;
import com.decagon.servletmvcsq019.dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name ="user", value = "/user")
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login")==null?null:req.getParameter("login");
        if (login!=null){
            RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
            dispatcher.forward(req, resp);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("sign-up.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Displaying user information now...");
        // "^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$"
        //conditional rendering...
       UserDto userDto = new UserDto();
       userDto.setName(req.getParameter("username"));
       userDto.setPassword(req.getParameter("password"));
       userDto.setEmail(req.getParameter("email"));
       UserServiceImpl userService = new UserServiceImpl();
       System.out.println(userService.saveUserInformation.apply(userDto));
        RequestDispatcher dispatcher = req.getRequestDispatcher("successful.jsp");
        dispatcher.forward(req, resp);

    }
}