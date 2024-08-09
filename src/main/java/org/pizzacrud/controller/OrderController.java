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

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") int id) {
        Order order = orderService.findById(id);
        OrderDto orderDto = orderMapper.toDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderMapper.toEntity(orderDto);
        Order orderSaved = orderService.save(order);
        OrderDto orderDtoSaved = orderMapper.toDto(orderSaved);
        return new ResponseEntity<>(orderDtoSaved, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OrderDto> deleteOrder(@PathVariable("id") int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
