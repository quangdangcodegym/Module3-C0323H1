package com.codegym.manager_products.service;

import com.codegym.manager_products.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(int id);
}
