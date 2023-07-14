package com.codegym.customermanager.service;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.model.CustomerType;
import com.codegym.customermanager.model.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerServiceMysql implements ICustomerService {
    private static final String FIND_ALL_CUSTOMERS = "SELECT c.*, ct.id as id_cate, ct.name as name_cate " +
            "FROM customers c join customer_types ct on c.id_customer_type = ct.id;";
    private static final String FIND_BY_ID = "SELECT c.*, ct.id as id_cate, ct.name as name_cate FROM customers c join customer_types ct on c.id_customer_type = ct.id  where  c.id = ?;";
    private static final String SQL_FILTER_CUSTOMERS = "SELECT c.*, ct.id as id_cate, ct.name as name_cate \n" +
            "FROM customers c join customer_types ct on c.id_customer_type = ct.id \n" +
            "where (c.name like ? or c.email like ? or c.address like ? ) %s \n" +
            "limit ?,?;  ";

    private static final String SQL_FILTER_CUSTOMERS_COUNT = "SELECT count(*) as total " +
            "FROM customers c join customer_types ct on c.id_customer_type = ct.id \n" +
            "where (c.name like ? or c.email like ? or c.address like ? ) %s \n";
    private String jdbcURL = "jdbc:mysql://localhost:3306/c3_shopmanager?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Raisingthebar123!!/";

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection  = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CUSTOMERS);

            System.out.println("findAll: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();        // khi trả về 1 hoặc nhiều dòng

            // rs.next(): kiểm tra coi có dòng tiếp theo ko
            while (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);

                customers.add(customer);
            }
            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }
        return customers;
    }

    private Customer getCustomerFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");     // chi so: 2
        String email = rs.getString("email");
        String address = rs.getString("address");

        Customer customer = new Customer(id, name, email, address);
        int idCate = rs.getInt("id_cate");
        String nameCate = rs.getString("name_cate");

        CustomerType customerType = new CustomerType(idCate, nameCate);
        customer.setCustomerType(customerType);
        return customer;
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
    @Override
    public void save(Customer customer) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `customers` (`name`, `email`, `address`, `id_customer_type`) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4, customer.getCustomerType().getId());

            System.out.println("save: " + preparedStatement);
            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException exception) {
            printSQLException(exception);
        }

    }

    @Override
    public Customer findById(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);

            preparedStatement.setInt(1, id);

            System.out.println("findById: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int idCustomer = rs.getInt("id");
                String name = rs.getString("name");     // chi so: 2
                String email = rs.getString("email");
                String address = rs.getString("address");
                Customer customer = new Customer(idCustomer, name, email, address);

                int idCate = rs.getInt("id_cate");
                String nameCate = rs.getString("name_cate");
                CustomerType ct = new CustomerType(idCate, nameCate);

                customer.setCustomerType(ct);
                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(int id, Customer customer) {

        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `customers` SET `name` = ?, `email` = ?, `address` = ?, `id_customer_type` = ? WHERE (`id` = ?);\n");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setInt(4,customer.getCustomerType().getId());
            preparedStatement.setInt(5, id);

            System.out.println("update: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
        }catch (SQLException exception) {
            printSQLException(exception);
        }
    }

    @Override
    public void remove(int id) {

        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `customers` WHERE (`id` = ?);");
            preparedStatement.setInt(1,id);

            System.out.println("remove: " + preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();

        }catch (SQLException exception){
            printSQLException(exception);
        }

    }

    @Override
    public List<Customer> findCustomers(Pageable pageable) {
        List<Customer> customers = new ArrayList<>();
        try {
            Connection connection = getConnection();
//            private static final String SQL_FILTER_CUSTOMERS = "SELECT c.*, ct.id as id_cate, ct.name as name_cate \n" +
//                    "FROM customers c join customer_types ct on c.id_customer_type = ct.id\n" +
//                    "where (c.name like ? || c.email like ? || c.address like ? ) %s" +
//                    "limit ?,?;  ";


            // Lấy danh sách dữ liệu
            String strCustomerType = "";
            if (pageable.getCustomerType() != -1) {
                strCustomerType = "and c.id_customer_type = " + pageable.getCustomerType();
            }
            String fmSQL_FILTER = String.format(SQL_FILTER_CUSTOMERS, strCustomerType);
            PreparedStatement ps = connection.prepareStatement(fmSQL_FILTER);
            ps.setString(1, "%" + pageable.getKw() + "%");
            ps.setString(2, "%" + pageable.getKw() + "%");
            ps.setString(3, "%" + pageable.getKw() + "%");
            ps.setInt(4, (pageable.getPage() - 1)*pageable.getLimit());
            ps.setInt(5, pageable.getLimit());

            System.out.println("findCustomers: " + ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = getCustomerFromResultSet(rs);
                customers.add(customer);
            }

            // Thực hiện đoạn này để tính tổng số dòng
            fmSQL_FILTER = String.format(SQL_FILTER_CUSTOMERS_COUNT, strCustomerType);
            ps = connection.prepareStatement(fmSQL_FILTER);
            ps.setString(1, "%" + pageable.getKw() + "%");
            ps.setString(2, "%" + pageable.getKw() + "%");
            ps.setString(3, "%" + pageable.getKw() + "%");

            System.out.println("findCustomers Filter count row: " + ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                int total = rs.getInt("total");
                //-- 8 dong/3 => 2,7 => 3
                pageable.setTotal((int) Math.ceil((total*1.0)/pageable.getLimit()));
            }


        } catch (SQLException sqlException) {
            printSQLException(sqlException);
        }
        return customers;
    }
}
