package com.decagon.servletmvcsq019.service;

import java.sql.SQLException;

@FunctionalInterface
public interface UserService {
    void compile() throws SQLException, ClassNotFoundException;
}
