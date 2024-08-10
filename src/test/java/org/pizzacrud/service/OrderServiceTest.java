package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.service.configuration.OrderServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(OrderServiceTestConfiguration.class)
class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private Order testOrder;


    @Test
    void findByIdTest() {
        Order order = orderService.findById(1);
        assertEquals(1, order.getId());
    }

    @Test
    void findByIdExceptionTest() {
        int invalidId = 1000;
        assertThrows(EntityNotFoundException.class, () -> orderService.findById(invalidId));
    }

    @Test
    void saveTest() {
        Order order = orderService.save(testOrder);
        assertEquals(1, order.getId());
    }

    @Test
    void delete() {
        assertDoesNotThrow(()->orderService.delete(1));
    }

    @Test
    void deleteExceptionTest() {
        int invalidId = 1000;
        assertThrows(EntityNotFoundException.class, ()->orderService.delete(invalidId));
    }
}
