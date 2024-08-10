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

    /**
     * Find all Customers
     * @return List of Customers
     */
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Find Customer by id
     * @param id id of the Customer
     * @return
     */
    @Override
    public Customer findById(int id) {
        return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Create new Customer
     * @param customer customer to be saved
     * @return Saved customer
     */
    @Override
    public Customer create(Customer customer) {
        customer.setId(0);
        customer.getAddress().setId(0);
        customer.getAddress().setCustomer(customer);
        return customerRepository.save(customer);
    }

    /**
     * Update existing Customer by id provided in params
     * Customer id will be set to param's id
     * @param id id of the Customer
     * @param customer customer data
     * @return
     */
    @Override
    public Customer update(int id, Customer customer) {
        customer.setId(id);
        customer.getAddress().setCustomer(customer);
        customer.getAddress().setId(id);
        return customerRepository.save(customer);
    }

    /**
     * Delete Customer by its id
     * @param id id of the Customer
     */
    @Override
    public void delete(int id) {
        customerRepository.deleteById(id);
    }
}
