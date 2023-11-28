package com.decagon.servletmvcsq019.dao;

import com.decagon.servletmvcsq019.config.DatabaseConfiguration;
import com.decagon.servletmvcsq019.dto.LoginRequestDto;
import com.decagon.servletmvcsq019.models.Product;
import com.decagon.servletmvcsq019.models.Users;
import com.decagon.servletmvcsq019.service.UserService;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Logger;

public class ProductDao {

    private Logger logger = Logger.getGlobal();
    private Connection connection;

    public UserService connect = () -> {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DatabaseConfiguration configuration = new DatabaseConfiguration();
        Properties properties = new Properties();
        properties.setProperty("User", configuration.getDB_URL());
        properties.setProperty("Password", configuration.getDB_PASSWORD());
        if (connection==null|| connection.isClosed()){
            connection = DriverManager.getConnection(configuration.getDB_URL(), configuration.getDB_USERNAME(), configuration.getDB_PASSWORD());
        }
    };


    public Function<Product, Boolean> saveProduct = (product -> {
        try {
            connect.compile();
        } catch (SQLException | ClassNotFoundException e) {
            logger.warning("SQL exception"+e.getMessage());
            throw new RuntimeException(e);
        }
        String query = "INSERT INTO ProductDB.users  (name, email, password) VALUES (?, ?, ?) ";

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getProductPrice().doubleValue());
            preparedStatement.setLong(3, product.getUserId());
            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    });


//    public Function<Users, List<Product>> findUser = (users -> {
//        try {
//            connect.compile();
//        } catch (SQLException | ClassNotFoundException e) {
//            logger.warning("SQL exception"+e.getMessage());
//            throw new RuntimeException(e);
//        }
//        String query = "SELECT * FROM ProductDB.users WHERE userId = ?";
//
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, users.getEmail());
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                return Users.builder()
//                        .id(resultSet.getLong(1))
//                        .email(resultSet.getString(2))
//                        .password(resultSet.getString(3))
//                        .name(resultSet.getString(4)).build();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    });
}
