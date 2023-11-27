package com.decagon.servletmvcsq019;

import com.decagon.servletmvcsq019.dto.UserDto;
import com.decagon.servletmvcsq019.models.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class UserServiceImpl {

    public static List<Users> savedUsers = new ArrayList<>();
    public Function<UserDto, Users> saveUserInformation = (userDto -> {
        Users user = new Users(userDto);
        user.setId(savedUsers.size()==0?1: (long) savedUsers.size()+1);
        savedUsers.add(user);
        return user;
    });
}
