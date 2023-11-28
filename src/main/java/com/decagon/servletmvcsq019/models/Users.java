package com.decagon.servletmvcsq019.models;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.decagon.servletmvcsq019.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Users(UserDto signedUpUser) {
        this.name = signedUpUser.getName();
        this.email = signedUpUser.getEmail();
        String password = BCrypt.withDefaults().hashToString(12, signedUpUser.getPassword().toCharArray());
        this.password = password;
    }
}
