package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    List<Customer> findAll();
}
