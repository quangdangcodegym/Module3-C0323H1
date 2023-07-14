package com.codegym.manager_products.controller;

import com.codegym.manager_products.appconfig.AppConfig;
import com.codegym.manager_products.model.*;
import com.codegym.manager_products.service.*;
import com.codegym.manager_products.utils.ValidatesUtils;

import javax.jws.Oneway;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name ="ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    private IProductService productService = new ProductServiceMysql();
    private ICategoryService categoryService = new CategoryServiceMysql();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        boolean isAdmin = user != null && user.getRole() == ERole.ROLE_ADMIN;
        if (!isAdmin) {
            resp.sendRedirect("/login");
            return;
        }

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
                deleteProduct(req, resp);
                break;
            default:
                showList(req, resp);

        }
    }

    private void showEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       long idProduct = Long.parseLong(req.getParameter("id"));

        Product product = productService.findById(idProduct);
        req.setAttribute("product", product);

        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        ESize[] sizes = ESize.values();
        req.setAttribute("sizes", sizes);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/edit.jsp");
        requestDispatcher.forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        long idProduct = Long.parseLong(req.getParameter("id"));
//
//        Product product = productService.findById(idProduct);
//        req.setAttribute("product", product);

        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        ESize[] sizes = ESize.values();
        req.setAttribute("sizes", sizes);


        List<Product> productList = productService.findAll();
        req.setAttribute("products", productList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(AppConfig.VIEW_DASHBOARD + "products/create.jsp");
        requestDispatcher.forward(req, resp);
    }
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        productService.remove(id);

        req.getSession().setAttribute("messageDelete", "Xóa thành công");
        resp.sendRedirect("/products");
    }
    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = productService.findAll();
        req.setAttribute("products", productList);

        ESize[] sizes = ESize.values();
        req.setAttribute("sizes", sizes);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher(AppConfig.VIEW_DASHBOARD + "products/list.jsp");
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
                saveProduct(req, resp);
                break;
            case "edit":
                updateProduct(req, resp);
                break;
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Product product = new Product();
        List<String> errors = new ArrayList<>();

        validateIdProduct(req, product, errors);
        validateNameProduct(req, product, errors);
        validateDesProduct(req, product, errors);
        validatePriceProduct(req, product, errors);
        validateSizeProduct(req, product, errors);
        validateCateProduct(req, product, errors);
       



        String createAtStr = req.getParameter("createAt");
        LocalDate createAt = LocalDate.parse(createAtStr);

        LocalDateTime now = LocalDateTime.now();
        Instant updateAtI = now.atZone(ZoneId.systemDefault()).toInstant();


        product.setCreateAt(createAt);
        product.setUpdateAt(updateAtI);

//        int idSize = Integer.parseInt(req.getParameter("size"));
//        ESize eSize = ESize.findById(idSize);
//        product.setSize(eSize);
        
        if(!errors.isEmpty()){
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories" , categories);
            ESize[] sizes = ESize.values();
            req.setAttribute("sizes", sizes);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/edit.jsp");
            requestDispatcher.forward(req, resp);
        }
        else {
            productService.update(product.getId(), product);
            req.setAttribute("updateAt", updateAtI);
            req.getSession().setAttribute("messageEdit", "Sửa thành công");
            resp.sendRedirect("/products");            // Dùng respone để sendRedirect
        }




    }

    private void validateSizeProduct(HttpServletRequest req, Product product, List<String> errors) {
        ESize eSize = null;
        try {
            // idCate có 2 trường hợp: "aaa" hoặc mã ko hợp lệ ko có trong DB 20000
            int idSize = Integer.parseInt(req.getParameter("size"));
            eSize = ESize.findById(idSize);
            if (eSize == null) {
                errors.add("size sản phẩm không có trong csdl");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng của size sản phẩm không hợp lệ");
        }
        product.setSize(eSize);
    }

    private void validateCateProduct(HttpServletRequest req, Product product, List<String> errors) {
        Category ct = null;
        try {
            // idCate có 2 trường hợp: "aaa" hoặc mã ko hợp lệ ko có trong DB 20000
            int idCate = Integer.parseInt(req.getParameter("category"));
            ct = categoryService.findById(idCate);
            if (ct == null) {
                errors.add("Loại sản phẩm không có trong csdl");
            }
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng của loại sản phẩm không hợp lệ");
        }
        product.setCategory(ct);
    }

    private void validatePriceProduct(HttpServletRequest req, Product product, List<String> errors) {
        try{
            String priceString = req.getParameter("price");
            if (!ValidatesUtils.isPriceValid(priceString)) {
                errors.add("Giá không hợp lệ, là số bắt đầu khác 0 và phải có từ 5-9 kí tự số");
            }
            BigDecimal price = new BigDecimal(priceString);
            product.setPrice(price);
        }catch (NumberFormatException n){
            errors.add("Định dạng giá không hợp lệ");
        }

    }

    private void validateDesProduct(HttpServletRequest req, Product product, List<String> errors) {
        String description = req.getParameter("description");
        if (!ValidatesUtils.isDesValid(description)) {
            errors.add("Mô tả không hợp lệ, bắt đầu bằng chữ cái và phải có từ 15-50 kí tự");
        }
        product.setDescription(description);
    }

    private void validateNameProduct(HttpServletRequest req, Product product, List<String> errors) {
        String name = req.getParameter("name");
        if (!ValidatesUtils.isNameValid(name)) {
            errors.add("Tên không hợp lệ, bắt đầu bằng chữ cái và phải có từ 8-20 kí tự");
        }
        product.setName(name);
    }

    private void validateIdProduct(HttpServletRequest req, Product product, List<String> errors) {
        try {
            long id = Long.parseLong(req.getParameter("id"));
            if (productService.findById(id)==null) {
                errors.add("Mã sản phẩm không hợp lệ");
            }
            product.setId(id);
        } catch (NumberFormatException numberFormatException) {
            errors.add("Định dạng mã sản phẩm không hợp lệ");
        }
    }

    private void saveProduct(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Product product = new Product();
        List<String> errors = new ArrayList<>();

//        validateIdProduct(req, product, errors);
        validateNameProduct(req, product, errors);
        validateDesProduct(req, product, errors);
        validatePriceProduct(req, product, errors);
        validateSizeProduct(req, product, errors);
        validateCateProduct(req, product, errors);




//        String name = req.getParameter("name");
//        String description = req.getParameter("description");
//        String priceString = req.getParameter("price");
//        BigDecimal price = new BigDecimal(priceString);

//        String createAtStr = req.getParameter("createAt");
//        LocalDate createAt = LocalDate.parse(createAtStr);

//        String updateAtStr = req.getParameter("updateAt");
//        LocalDate date = LocalDate.parse(updateAtStr);
//        LocalDateTime dateTime = date.atStartOfDay();
//        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneOffset.UTC);
//        String updateAtStr1 = zonedDateTime.format(DateTimeFormatter.ISO_INSTANT);
//        Instant updateAt = Instant.parse(updateAtStr1);

        LocalDate now = LocalDate.now();
        product.setCreateAt(now);
        LocalDateTime now1 = LocalDateTime.now();
        Instant updateAt = now1.atZone(ZoneId.systemDefault()).toInstant();
        product.setUpdateAt(updateAt);


//        long id = (long)(Math.random() * 10000);
//        Product product = new Product(id, name, description, price, createAt, updateAt);

//        int idSize = Integer.parseInt(req.getParameter("size"));
//        ESize eSize = ESize.findById(idSize);
//        product.setSize(eSize);
//
//        int idCate = Integer.parseInt(req.getParameter("category"));
//        Category category = categoryService.findById(idCate);
//        product.setCategory(category);
//        productService.save(product);

        ESize[] sizes = ESize.values();
        req.setAttribute("sizes", sizes);

        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);

        if(!errors.isEmpty()){
            req.setAttribute("errors", errors);
            req.setAttribute("product", product);
        }else {
            req.setAttribute("message", "Thêm thành công");
            productService.save(product);
        }


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/products/create.jsp");
        requestDispatcher.forward(req, resp);
    }
}
