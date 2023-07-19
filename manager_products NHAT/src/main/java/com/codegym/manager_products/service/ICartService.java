package com.codegym.manager_products.service;

import com.codegym.manager_products.model.Cart;

public interface ICartService {

    Cart getCartById(long idUser);
    Cart createCart(Cart cart);

    Cart updateCart(Cart cart);

    void addToCart(int idProduct, int quantity, long id);

    Cart updateCartInfo(long id, long idProduct, int quantity);
}
