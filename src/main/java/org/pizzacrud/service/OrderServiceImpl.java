package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.database.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Order save(Order order) {
        order.setId(0);
        return orderRepository.save(order);
    }

    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
