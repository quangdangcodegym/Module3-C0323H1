package com.codegym.manager_products.controller.auth;

import com.codegym.manager_products.appconfig.AppConfig;
import com.codegym.manager_products.model.ERole;
import com.codegym.manager_products.model.User;
import com.codegym.manager_products.service.IUserService;
import com.codegym.manager_products.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private IUserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (userService.findUserByEmail(email) == null) {
            User user = new User(-1, null, password, email);
            user.setRole(ERole.ROLE_USER);
            userService.createUser(user);

            req.getSession().setAttribute("user", user);
            resp.sendRedirect("/home");
        }else{
            // báo lỗi đã tồn tại
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "register.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
