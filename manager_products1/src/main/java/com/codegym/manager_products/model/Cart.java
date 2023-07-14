package com.codegym.manager_products.model;

import java.time.LocalDate;
import java.util.List;

public class Cart {
    private long id;
    private LocalDate createAt;
    List<CartItem> cartItems;

    private double total;



}
