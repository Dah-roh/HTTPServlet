package com.decagon.servletmvcsq019.config;

import lombok.Getter;

@Getter
public class DatabaseConfiguration {
    /*
    This class contains database configuration credentials: Database url, user, and password.
     */
    private final String DB_URL = "jdbc:mysql://localhost:3306/ProductDB";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "password";
}
