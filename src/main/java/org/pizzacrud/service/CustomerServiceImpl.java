package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Customer create(Customer customer) {
        customer.setId(0);
        customer.getAddress().setId(0);
        customer.getAddress().setCustomer(customer);
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(int id, Customer customer) {
        customer.setId(id);
        customer.getAddress().setCustomer(customer);
        customer.getAddress().setId(id);
        return customerRepository.save(customer);
    }

    @Override
    public void delete(int id) {
        customerRepository.deleteById(id);
    }
}
