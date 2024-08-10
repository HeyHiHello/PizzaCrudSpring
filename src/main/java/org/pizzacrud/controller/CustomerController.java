package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.dto.AddressDto;
import org.pizzacrud.dto.CustomerDto;
import org.pizzacrud.dto.OrderDto;
import org.pizzacrud.mapper.CustomerMapper;
import org.pizzacrud.mapper.OrderMapper;
import org.pizzacrud.service.CustomerService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that serve crud requests on Customer entity
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, OrderMapper orderMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.orderMapper = orderMapper;
    }

    /**
     * Get all Customers in DB
     * @return ResponseEntity with List of Customers and response code OK
     */
    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<Customer> allCustomers = customerService.findAll();
        List<CustomerDto> customerDtos = customerMapper.toDtoList(allCustomers);
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    /**
     * Get Customer by id
     * @param id id of the Customer
     * @return ResponseEntity with requested Customer and response code OK
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") int id) {
        Customer customer = customerService.findById(id);
        CustomerDto customerDto = customerMapper.toDto(customer);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    /**
     * Get Customer Address by Customer id
     * @param customerId id of the customer
     * @return ResponseEntity with Customer's Address and response code OK
     */
    @GetMapping("{id}/address")
    public ResponseEntity<AddressDto> getCustomerAddress(@PathVariable("id") int customerId) {
        Customer customer = customerService.findById(customerId);
        CustomerDto customerDto = customerMapper.toDto(customer);
        return new ResponseEntity<>(customerDto.address(),  HttpStatus.OK);
    }

    /**
     * Get All Customer's Orders by Customer id
     * @param customerId id of the customer
     * @return ResponseEntity with List of Orders of the Customer and response code OK
     */
    @GetMapping("{id}/orders")
    ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable("id") int customerId) {
        Customer customer = customerService.findById(customerId);
        List<Order> customerOrders = customer.getOrders();
        List<OrderDto> orderDtos = orderMapper.toDtoList(customerOrders);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    /**
     * Create new customer
     * @param customerDto Request body with Customer data
     * @return ResponseEntity of created Customer and response code CREATED
     */
    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        customerService.create(customer);
        CustomerDto dto = customerMapper.toDto(customer);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Update Customer by its id
     * @param id id of the Customer
     * @param customerDto Request body with Customer data
     * @return ResponseEntity with created customer and response code OK
     */
    @PatchMapping("{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") int id, @RequestBody CustomerDto customerDto) {
        Customer customer = customerMapper.toEntity(customerDto);
        Customer updatedCustomer = customerService.update(id, customer);
        CustomerDto dto  = customerMapper.toDto(updatedCustomer);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Delete Customer by its id
     * @param id id of the Customer
     * @return Empty ResponseEntity and response code NO CONTENT
     */
    @DeleteMapping("{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable("id") int id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Response didn't find Customer by its id
     * @param e Exception
     * @return ResponseEntity with response code NOT FOUND
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
