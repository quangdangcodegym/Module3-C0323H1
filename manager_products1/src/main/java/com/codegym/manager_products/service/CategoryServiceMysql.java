package com.codegym.manager_products.service;

import com.codegym.manager_products.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class CategoryServiceMysql extends DbContext implements ICategoryService{
    private static final String FIND_ALL = "SELECT * FROM categories";
    private static final String FIND_BY_ID = "SELECT * FROM categories WHERE  id = ?";

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);

            System.out.println("findAll: "+ preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int idCate = rs.getInt("id");
                String name = rs.getString("name");
                Category category = new Category(idCate, name);

                categories.add(category);
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return categories;
    }

    @Override
    public Category findById(int id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1,id);

            System.out.println("findById: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int idCate = rs.getInt("id");
                String nameCate = rs.getString("name");
                Category category = new Category(idCate, nameCate);
                return category;
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return null;
    }

}
