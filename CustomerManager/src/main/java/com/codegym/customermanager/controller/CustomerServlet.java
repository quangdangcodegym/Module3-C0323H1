package com.codegym.customermanager.controller;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.model.CustomerType;
import com.codegym.customermanager.service.CustomerTypeServiceMysql;
import com.codegym.customermanager.service.ICustomerService;
import com.codegym.customermanager.service.CustomerServiceMysql;
import com.codegym.customermanager.service.ICustomerTypeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", urlPatterns = "/customers")
public class CustomerServlet extends HttpServlet {
    private ICustomerService customerService = new CustomerServiceMysql();
    private ICustomerTypeService customerTypeService = new CustomerTypeServiceMysql();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //localhost:8080/customers          // show list
        //localhost:8080/customers?action=create
        //localhost:8080/customers?action=edit&id=2
        //localhost:8080/customers?action=delete&id=2
        //localhost:8080/customers?action=advavd            // show list
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;                  // LUÔN NHỚ Có BREAK
            case "edit":
                showEdit(req, resp);
                break;
            case "delete":
                deleteCustomer(req, resp);
                break;
            default:
                showList(req, resp);

        }

    }

    private void deleteCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        customerService.remove(id);

        req.getSession().setAttribute("messageDelete", "Xóa thành công");
        resp.sendRedirect("/customers");
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idCustomer = Integer.parseInt(req.getParameter("id"));

        Customer customer = customerService.findById(idCustomer);
        req.setAttribute("customer", customer);

        List<CustomerType> customerTypes = customerTypeService.findAll();

        req.setAttribute("customerTypes", customerTypes);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/edit.jsp");
        requestDispatcher.forward(req, resp);

    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = customerService.findAll();
        req.setAttribute("customers", customerList);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/list.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CustomerType> customerTypes = customerTypeService.findAll();

        req.setAttribute("customerTypes", customerTypes);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                saveCustomer(req, resp);
                break;
            case "edit":
                updateCustomer(req, resp);
                break;
        }
    }

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        Customer customer = customerService.findById(id);
        customer.setAddress(address);
        customer.setName(name);
        customer.setEmail(email);

        int idCate = Integer.parseInt(req.getParameter("customer-type"));
        CustomerType ct = customerTypeService.findById(idCate);

        customer.setCustomerType(ct);
        customerService.update(id, customer);
        req.getSession().setAttribute("messageEdit", "Sửa thành công");
        resp.sendRedirect("/customers");            // Dùng respone để sendRedirect


    }

    private void saveCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        int id = (int)(Math.random() * 10000);
        Customer customer = new Customer(id, name, email, address);

        int idCate = Integer.parseInt(req.getParameter("customer-type"));
        CustomerType ct = customerTypeService.findById(idCate);

        customer.setCustomerType(ct);
        customerService.save(customer);

        List<CustomerType> customerTypes = customerTypeService.findAll();

        req.setAttribute("customerTypes", customerTypes);
        req.setAttribute("message", "Thêm thành công");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/create.jsp");
        requestDispatcher.forward(req, resp);
    }
}
