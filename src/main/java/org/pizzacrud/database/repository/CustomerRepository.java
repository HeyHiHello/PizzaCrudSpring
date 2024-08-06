package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
