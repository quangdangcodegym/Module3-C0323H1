package com.codegym.manager_products.service;

import com.codegym.manager_products.model.User;

public interface IUserService {
    User findUserByEmail(String email);

    void createUser(User user);

    void updateUser(long id, User user);
}
