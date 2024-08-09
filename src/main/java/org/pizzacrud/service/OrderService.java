package org.pizzacrud.service;

import org.pizzacrud.database.entity.Order;

public interface OrderService {
    Order findById(int id);
    Order save(Order order);
    void delete(int id);
}
