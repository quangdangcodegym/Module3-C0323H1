package com.codegym.manager_products.model;

import java.time.LocalDate;
import java.util.List;

public class Bill {
    private long id;
    private LocalDate createAt;
    List<CartItem> cartItems;

    private double total;
    private String fullName;
    private String phone;
}
