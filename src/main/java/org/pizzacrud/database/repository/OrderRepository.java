package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
