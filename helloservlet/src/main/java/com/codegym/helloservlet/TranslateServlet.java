package com.codegym.helloservlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "TranslateServlet", urlPatterns = "/translate")
public class TranslateServlet extends HttpServlet {
    private Map<String, String> dictionary;

    public TranslateServlet() {
        dictionary = new HashMap<>();
        dictionary.put("hello", "Xin chao");
        dictionary.put("yellow", "Mau vang");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/translate\" method=\"post\">\n" +
                "        <input type=\"text\" name=\"kw\" />\n" +
                "        <button type=\"submit\">Transalte</button>\n" +
                "    </form>\n" +
                "</body>\n" +
                "</html>");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        String kw = req.getParameter("kw");         // HELLO
        String result = dictionary.get(kw.toLowerCase());

        String strOutputOriginal = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <form action=\"/translate\" method=\"post\">\n" +
                "        <input type=\"text\" name=\"kw\" value='%s'/>\n" +
                "        <button type=\"submit\">Transalte</button>\n" +
                "    </form>\n" +
                "    <label>%s</label>\n" +
                "</body>\n" +
                "</html>";
        String str = String.format(strOutputOriginal,kw, "Kết quả: " + result);
        PrintWriter out = resp.getWriter();
        out.println(str);
    }
}
