package com.codegym.manager_products.service;

import com.codegym.manager_products.model.User;
import com.codegym.manager_products.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService extends DbContext implements IUserService{


    private static final String SQL_FIND_USERPASS = "SELECT * \n" +
            "FROM `user` \n" +
            "where `email` like ?";
    private static final String SQL_FIND_EMAIL = "SELECT * \n" +
            "FROM `user` \n" +
            "where `email` like ? ;";


    @Override
    public User findUserByEmail(String email) {
        try {
            Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_FIND_EMAIL);
            ps.setString(1, "%" + email + "%");
            System.out.println("findUserByEmail: " + ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long idDB = rs.getLong("id");
                String usernameDB = rs.getString("name");
                String passwordDB = rs.getString("password");
                String emailDB = rs.getString("email");

                return new User(idDB, usernameDB, passwordDB, emailDB);
            }

        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return null;
    }

    @Override
    public void createUser(User user) {
        try {
            Connection connection = getConnection();

            // mã hóa
            String strPass = PasswordUtils.hashPassword(user.getPassword());
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `user` (`email`, `password`, `role`) VALUES (?, ?, ?)");


            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, strPass);
            preparedStatement.setString(3, user.getRole().toString());


            System.out.println("createUser: " + preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
    }

    @Override
    public void updateUser(long id, User user) {

    }
}
