package com.codegym.manager_products.model;

import java.math.BigDecimal;

public class CartItem {
    private long id;
    private long idProduct;
    private Product product;
    private long idCart;

    private BigDecimal price;
    private int quantity;


    public CartItem(long id, long idProduct, long idCart, BigDecimal price, int quantity) {
        this.id = id;
        this.idProduct = idProduct;
        this.idCart = idCart;
        this.price = price;
        this.quantity = quantity;
    }
    public CartItem(long idProduct, long idCart, BigDecimal price, int quantity) {
        this.idProduct = idProduct;
        this.idCart = idCart;
        this.price = price;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CartItem(int idProduct, BigDecimal price, int quantity) {
        this.idProduct  = idProduct;
        this.price = price;
        this.quantity = quantity;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public long getIdCart() {
        return idCart;
    }

    public void setIdCart(long idCart) {
        this.idCart = idCart;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
