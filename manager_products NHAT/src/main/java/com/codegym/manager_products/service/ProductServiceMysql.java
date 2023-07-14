package com.codegym.manager_products.service;

import com.codegym.manager_products.model.Category;
import com.codegym.manager_products.model.ESize;
import com.codegym.manager_products.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceMysql extends DbContext implements IProductService{
    private static final String FIND_ALL_CUSTOMERS = "SELECT p.*, c.id as id_cate, c.name as name_cate " +
            "FROM products p join categories c on p.id_category = c.id";
//    private static final String SAVE_PRO = "INSERT INTO `products` (`name`, `description`, `price`, `createAt`, `id_category`, `size`) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SAVE_PRO = "INSERT INTO `products` (`name`, `description`, `price`, `createAt`, `id_category`, `size`, `updateAt`) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String FIND_BY_ID = "SELECT p.*, c.id as id_cate, c.name as name_cate " +
            "FROM products p join categories c on p.id_category = c.id WHERE p.id = ?;";
    private static final String UPDATE_PRO = "UPDATE `products` SET `name` = ?, `description` = ?, `price` = ?, `createAt` = ?, `id_category` = ?, `size` = ?, `updateAt` = ? WHERE (`id` = ?);";
    private static final String REMOVE_PRO = "DELETE FROM `products` WHERE (`id` = ?);";


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CUSTOMERS);
            System.out.println("findAll: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                java.sql.Date createAt = rs.getDate("createAt");
                LocalDate createAtL = createAt.toLocalDate();
                java.sql.Date updateAt = rs.getDate("updateAt");
//                Instant updateAtI = updateAt.toInstant();
                LocalDate localDate = updateAt.toLocalDate();
                LocalDateTime localDateTime = localDate.atStartOfDay();
                Instant updateAtI = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
                String size = rs.getString("size");
                ESize eSize = ESize.find(size);


                Product product = new Product(id, name, description, price, createAtL, updateAtI );

                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");
                Category category = new Category(idCate, nameCate);

                product.setSize(eSize);
                product.setCategory(category);
                products.add(product);
            }
            connection.close();
        }catch (SQLException exception){
            printSQLException(exception);
        }
        return products;
    }



    @Override
    public void save(Product product) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_PRO);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());

            LocalDate localDate = product.getCreateAt();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(4, createAt);

            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.setString(6, product.getSize().getName());

            Instant instant = product.getUpdateAt();
            java.sql.Date updateAt = new java.sql.Date(instant.toEpochMilli());
            preparedStatement.setDate(7, updateAt);
            
            System.out.println("save: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public Product findById(long id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, (int)id);
            System.out.println("findById: "+ preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                int idProduct = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                java.sql.Date createAt = rs.getDate("createAt");
                LocalDate createAtL = createAt.toLocalDate();
                java.sql.Date updateAt = rs.getDate("updateAt");
//                Instant updateAtI = updateAt.toInstant();
                LocalDate localDate = updateAt.toLocalDate();
                LocalDateTime localDateTime = localDate.atStartOfDay();
                Instant updateAtI = localDateTime.atZone(ZoneId.systemDefault()).toInstant();

                String size = rs.getString("size");
                ESize eSize = ESize.find(size);

                Product product = new Product(id, name, description, price, createAtL, updateAtI );


                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");
                Category category = new Category(idCate, nameCate);
                product.setSize(eSize);
                product.setCategory(category);
                return product;
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return null;
    }

    @Override
    public void update(long id, Product product) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRO);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setBigDecimal(3, product.getPrice());
            LocalDate localDate = product.getCreateAt();
            java.sql.Date createAt = java.sql.Date.valueOf(localDate);
            preparedStatement.setDate(4, createAt);
            preparedStatement.setInt(5, product.getCategory().getId());
            preparedStatement.setString(6, product.getSize().getName());
            Instant instant = product.getUpdateAt();
            java.sql.Date updateAt = new java.sql.Date(instant.toEpochMilli());
            preparedStatement.setDate(7, updateAt);
            preparedStatement.setInt(8, (int) id);

            System.out.println("update: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    @Override
    public void remove(long id) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRO);
            preparedStatement.setInt(1, (int) id);

            System.out.println("remove: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            printSQLException(e);
        }
    }
}
