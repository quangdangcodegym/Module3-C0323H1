package com.codegym.translatejsp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "TranslateServlet", urlPatterns = "/translate")
public class TranslateServlet extends HttpServlet {
    private Map<String, String> dictionary;

    public TranslateServlet() {
        dictionary = new HashMap<>();
        dictionary.put("hello", "Xin chao");
        dictionary.put("yellow", "Mau vang");
    }


    @Override
    public void init() throws ServletException {
        System.out.println("Khởi tạo servlet");
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/translate.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Chạy vào hàm doPost");
        String kw = req.getParameter("kw");         // hello
        String result = dictionary.get(kw.toLowerCase());   // xin chao
        Set<String> keys = dictionary.keySet();

        req.setAttribute("result", result);         // nhét giá trị xin chào vào request theo tên thuộc tính là result
        req.setAttribute("kw", kw);
        req.setAttribute("relativeKeys", keys);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/translate.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    public void destroy() {
        System.out.println("Chạy vào hàm destroy");
    }
}
