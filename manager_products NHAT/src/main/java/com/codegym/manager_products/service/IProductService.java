package com.codegym.manager_products.service;

import com.codegym.manager_products.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    void save(Product product);

    Product findById(long id);

    void update(long id, Product product);

    void remove(long id);
}
