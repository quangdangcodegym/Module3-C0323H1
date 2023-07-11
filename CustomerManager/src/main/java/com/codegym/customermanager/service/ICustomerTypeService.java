package com.codegym.customermanager.service;

import com.codegym.customermanager.model.Customer;
import com.codegym.customermanager.model.CustomerType;

import java.util.List;

public interface ICustomerTypeService {
    List<CustomerType> findAll();
    CustomerType findById(int id);
}
