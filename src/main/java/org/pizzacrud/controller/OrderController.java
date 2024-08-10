package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.dto.OrderDto;
import org.pizzacrud.mapper.OrderMapper;
import org.pizzacrud.service.OrderService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that serve crud requests on Order entity
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    /**
     * Get Order by id
     * @param id id of the Order
     * @return ResponseEntity with requested Order and status code OK
     */
    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.findById(id);
        OrderDto orderDto = orderMapper.toDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    /**
     * Create new Order
     * @param orderDto Request body with order data
     * @return ResponseEntity with created Order and status code CREATED
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order orderSaved = orderService.save(order);
        OrderDto orderDtoSaved = orderMapper.toDto(orderSaved);
        return new ResponseEntity<>(orderDtoSaved, HttpStatus.CREATED);
    }

    /**
     * Delete Order by id
     * @param id id of the Order
     * @return ResponseEntity with status code NO CONTENT
     */
    @DeleteMapping("{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("id") int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Repository didn't find any Entity by given id
     * @param e Exception
     * @return ResponseEntity with status code NOT FOUND
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Insertion finished with error due to invalid request body
     * @param e Exception
     * @return ResponseEntity with response code BAD REQUEST
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Unknown server error
     * @param e exception
     * @return ResponseEntity with response code INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
