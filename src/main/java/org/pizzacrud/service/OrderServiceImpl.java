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

    /**
     * Find Order by its id
     * @param id id of the Order
     * @return Found Order
     */
    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Create new Order
     * @param order order data
     * @return Created Order
     */
    @Override
    public Order save(Order order) {
        order.setId(0);
        return orderRepository.save(order);
    }

    /**
     * Delete Order by its id
     * @param id id of the Order
     */
    @Override
    public void delete(int id) {
        orderRepository.deleteById(id);
    }
}
