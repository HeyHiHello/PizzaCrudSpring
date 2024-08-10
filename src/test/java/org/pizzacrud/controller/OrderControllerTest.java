package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.controller.configuration.OrderControllerTestConfiguration;
import org.pizzacrud.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(OrderControllerTestConfiguration.class)
class OrderControllerTest {
    @Autowired
    private OrderController orderController;
    @Autowired
    private OrderDto testOrderDto;

    @Test
    void getByIdTest() {
        ResponseEntity<OrderDto> responseEntity = orderController.getOrderById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createTest() {
        ResponseEntity<OrderDto> responseEntity = orderController.createOrder(testOrderDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteTest() {
        ResponseEntity<OrderDto> responseEntity = orderController.deleteOrder(1);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void entityNotFoundExceptionTest() {
        ResponseEntity<Object> responseEntity = orderController.handleEntityNotFoundException(new EntityNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<Object> responseEntity = orderController.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void exception() {
        ResponseEntity<Object> responseEntity = orderController.handleAllException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
