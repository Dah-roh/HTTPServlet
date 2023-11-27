package com.decagon.servletmvcsq019.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.servletmvcsq019.dto.UserDto;
import lombok.Data;

@Data
public class Users {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Users(UserDto signedUpUser) {
        this.name = signedUpUser.getName();
        this.email = signedUpUser.getEmail();
//        BCrypt bCrypt = new BCrypt.HashData()
        this.password = signedUpUser.getPassword();
    }
}
