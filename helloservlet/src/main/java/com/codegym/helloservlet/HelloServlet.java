package com.codegym.helloservlet;

import java.io.*;
import java.util.Enumeration;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/plain");

        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            System.out.println(name);
            if(name.toLowerCase().equals("sec-ch-ua") && request.getHeader("sec-ch-ua").toLowerCase().contains("Microsoft Edge".toLowerCase())){
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h1>" + "TAO CHẶN MI" + "</h1>");
                out.println("</body></html>");
                return;
            }
        }
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}