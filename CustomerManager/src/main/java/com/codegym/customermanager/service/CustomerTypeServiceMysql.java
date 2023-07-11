package com.codegym.customermanager.service;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.model.CustomerType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerTypeServiceMysql implements ICustomerTypeService{
    private static final String SELECT_ALL_CUSTOMER_TYPES = "SELECT * FROM customer_types;";
    private static final String FIND_BY_ID = "SELECT * FROM customer_types where id = ?;";
    private String jdbcURL = "jdbc:mysql://localhost:3306/c3_shopmanager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Raisingthebar123!!/";
    @Override
    public List<CustomerType> findAll() {
        List<CustomerType> customers = new ArrayList<>();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMER_TYPES);

            System.out.println("findAll: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                CustomerType ct = new CustomerType(idCate, name);

                customers.add(ct);
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return customers;
    }

    @Override
    public CustomerType findById(int id) {

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);

            System.out.println("findById: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCate = rs.getInt("id");
                String nameCate = rs.getString("name");
                CustomerType ct = new CustomerType(idCate, nameCate);
                return ct;
            }
        } catch (SQLException sqlException) {
            printSQLException(sqlException);

        }
        return null;
    }
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
