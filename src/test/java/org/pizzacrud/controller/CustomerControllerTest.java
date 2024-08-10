package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.controller.configuration.CustomerControllerTestConfiguration;
import org.pizzacrud.dto.AddressDto;
import org.pizzacrud.dto.CustomerDto;
import org.pizzacrud.dto.OrderDto;
import org.pizzacrud.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(CustomerControllerTestConfiguration.class)
class CustomerControllerTest {
    @Autowired
    CustomerController customerController;
    @Autowired
    CustomerDto testCustomerDto;
    @Autowired
    CustomerService mockCustomerService;

    @Test
    void getAllCustomersTest() {
        ResponseEntity<List<CustomerDto>> responseEntity = customerController.getAllCustomers();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void getCustomerByIdTest() {
        ResponseEntity<CustomerDto> responseEntity = customerController.getCustomerById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getCustomerAddressTest() {
        ResponseEntity<AddressDto> responseEntity = customerController.getCustomerAddress(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getCustomerOrdersTest() {
        ResponseEntity<List<OrderDto>> responseEntity = customerController.getCustomerOrders(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createCustomerTest() {
        ResponseEntity<CustomerDto> responseEntity = customerController.createCustomer(testCustomerDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void updateCustomerTest() {
        ResponseEntity<CustomerDto> responseEntity = customerController.updateCustomer(1, testCustomerDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteCustomerTest() {
        ResponseEntity<CustomerDto> responseEntity = customerController.deleteCustomer(1);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void entityNotFoundExceptionTest() {
        ResponseEntity<Object> responseEntity = customerController.handleEntityNotFoundException(new EntityNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<Object> responseEntity = customerController.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void exception() {
        ResponseEntity<Object> responseEntity = customerController.handleAllException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
