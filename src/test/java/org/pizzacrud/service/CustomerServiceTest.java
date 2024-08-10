package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.service.configuration.CustomerServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(CustomerServiceTestConfiguration.class)
class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private Customer testCustomer;

    @Test
    void getAllTest() {
        List<Customer> customers = customerService.findAll();
        assertEquals(2, customers.size());
    }

    @Test
    void getByIdTest() {
        Customer customer = customerService.findById(1);
        assertEquals(1, customer.getId());
        assertEquals(testCustomer.getFirstname(), customer.getFirstname());
        assertEquals(testCustomer.getLastname(), customer.getLastname());
    }

    @Test
    void getByIdExceptionTest() {
        assertThrows(EntityNotFoundException.class, () -> customerService.findById(2));
    }

    @Test
    void saveTest() {
        Customer customer = customerService.create(testCustomer);

        assertEquals(1, customer.getId());
        assertEquals(testCustomer.getFirstname(), customer.getFirstname());
        assertEquals(testCustomer.getLastname(), customer.getLastname());
    }

    @Test
    void updateTest() {
        Customer customer = customerService.update(1, testCustomer);
        assertEquals(1, customer.getId());
        assertEquals(testCustomer.getFirstname(), customer.getFirstname());
        assertEquals(testCustomer.getLastname(), customer.getLastname());
    }

    @Test
    void deleteTest() {
        assertDoesNotThrow(()->customerService.delete(1));
    }
}
