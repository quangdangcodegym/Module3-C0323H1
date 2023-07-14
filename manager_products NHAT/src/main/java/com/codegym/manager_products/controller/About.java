package com.codegym.manager_products.controller;

import com.codegym.manager_products.appconfig.AppConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "about", urlPatterns = "/about")
public class About extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "about.jsp").forward(req, resp);
    }
}
