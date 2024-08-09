package org.pizzacrud.service;

import org.pizzacrud.database.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(int id);
    Customer create(Customer customer);
    Customer update(int id, Customer customer);
    void delete(int id);
}
