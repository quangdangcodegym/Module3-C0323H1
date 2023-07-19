package com.codegym.manager_products.controller;

import com.codegym.manager_products.appconfig.AppConfig;
import com.codegym.manager_products.model.Cart;
import com.codegym.manager_products.model.User;
import com.codegym.manager_products.service.CartService;
import com.codegym.manager_products.service.ICartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    private ICartService iCartService;

    @Override
    public void init() throws ServletException {
        iCartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "add":
                addToCartView(req, resp);
                break;
            case "update":
                updateCartView(req, resp);
                break;
            default:
                showCartView(req, resp);
        }
    }

    private void updateCartView(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        long idProduct = Long.parseLong(req.getParameter("id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));


        Cart cart = iCartService.updateCartInfo(user.getId(), idProduct, quantity);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "cart.jsp").forward(req, resp);
    }

    private void showCartView(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        Cart cart = iCartService.getCartById(user.getId());
        req.setAttribute("cart", cart);
        req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "cart.jsp").forward(req, resp);
    }

    private void addToCartView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idProduct = Integer.parseInt(req.getParameter("id"));
        int quantity = 1;

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        iCartService.addToCart(idProduct, quantity, user.getId());

        Cart cart = iCartService.getCartById(user.getId());
        req.setAttribute("cart", cart);
        req.getRequestDispatcher(AppConfig.VIEW_FRONTEND + "cart.jsp").forward(req, resp);
    }
}
