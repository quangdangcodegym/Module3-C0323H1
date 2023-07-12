package com.codegym.customermanager.controller;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.model.CustomerType;
import com.codegym.customermanager.service.CustomerTypeServiceMysql;
import com.codegym.customermanager.service.ICustomerService;
import com.codegym.customermanager.service.CustomerServiceMysql;
import com.codegym.customermanager.service.ICustomerTypeService;
import com.codegym.customermanager.utils.ValidatesUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

    private void updateCustomer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Customer customer = new Customer();
        List<String> errors = new ArrayList<>();




        validateIdCustomer(req, customer, errors);

        validateName(req, errors, customer);
        validateEmail(req, errors, customer);
        validateCustomerType(req, errors, customer);
        validateAddress(req, errors, customer);


        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("customer", customer);
            List<CustomerType> customerTypes = customerTypeService.findAll();
            req.setAttribute("customerTypes", customerTypes);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/edit.jsp");
            requestDispatcher.forward(req, resp);
        }else{
            customerService.update(customer.getId(), customer);
            req.getSession().setAttribute("messageEdit", "Sửa thành công");
            resp.sendRedirect("/customers");            // Dùng respone để sendRedirect
        }



    }

    private void validateIdCustomer(HttpServletRequest req, Customer customer, List<String> errors) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if (customerService.findById(id)==null) {
                errors.add("Mã khách hàng không hợp lệ");
            }
            customer.setId(id);
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng mã khách hàng không hợp lệ");
        }

    }

    private void saveCustomer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer customer = new Customer();


        List<String> errors = new ArrayList<>();
        validateName(req, errors, customer);
        validateEmail(req, errors, customer);
        validateAddress(req, errors, customer);



        validateCustomerType(req, errors, customer);



        List<CustomerType> customerTypes = customerTypeService.findAll();
        req.setAttribute("customerTypes", customerTypes);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("customer", customer);
        }else{
            req.setAttribute("message", "Thêm thành công");
            customerService.save(customer);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/customers/create.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void validateCustomerType(HttpServletRequest req, List<String> errors, Customer customer) {
        CustomerType ct = null;
        try {
            // idCate có 2 trường hợp: "aaa" hoặc mã ko hợp lệ ko có trong DB 20000
            int idCate = Integer.parseInt(req.getParameter("customer-type"));
            ct = customerTypeService.findById(idCate);
            if (ct == null) {
                errors.add("Loại khách hàng không có trong csdl");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng của loại khách hàng không hợp lệ");
        }
        customer.setCustomerType(ct);


    }

    private void validateAddress(HttpServletRequest req, List<String> errors, Customer customer) {
        String address = req.getParameter("address");
        if (!ValidatesUtils.isAddressValid(address)) {
            errors.add("Địa chỉ không hợp lệ");
        }
        customer.setAddress(address);

    }

    private void validateEmail(HttpServletRequest req, List<String> errors, Customer customer) {
        String email = req.getParameter("email");
        if (!ValidatesUtils.isValidEmail(email)) {
            errors.add("Email không hợp lệ");

            // Có thể gửi từng field lỗi qua hoặc dùng map<fieldloi, tenloi>
//            req.setAttribute("errorEmail", "Email không hợp lệ");
        }
        customer.setEmail(email);

    }

    private void validateName(HttpServletRequest req, List<String> errors, Customer customer) {
        String name = req.getParameter("name");
        if (!ValidatesUtils.isUserNameValid(name)) {
            errors.add("Tên không hợp lệ, bắt đầu bằng chữ cái và phải có từ 8-20 kí tự");
        }
        customer.setName(name);
    }
}
