package com.codegym.manager_products.controller;

import com.codegym.manager_products.appconfig.AppConfig;
import com.codegym.manager_products.model.Product;
import com.codegym.manager_products.service.IProductService;
import com.codegym.manager_products.service.ProductServiceMysql;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private IProductService productService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceMysql();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productService.findAll();

        req.setAttribute("products", products);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
