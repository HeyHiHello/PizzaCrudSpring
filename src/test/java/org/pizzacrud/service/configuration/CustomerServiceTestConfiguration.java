package org.pizzacrud.service.configuration;

import org.pizzacrud.database.entity.Address;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.repository.CustomerRepository;
import org.pizzacrud.service.CustomerService;
import org.pizzacrud.service.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class CustomerServiceTestConfiguration {

    @Bean
    @Scope("prototype")
    Address testAddress() {
        Address address = new Address();
        address.setStreet("testStreet");
        address.setCity("testCity");
        address.setBuilding("testBuilding");
        return address;
    }

    @Bean
    @Scope("prototype")
    Customer testCustomer() {
        Customer customer = new Customer();
        customer.setFirstname("testFirstname");
        customer.setLastname("testLastname");
        customer.setAddress(testAddress());
        return customer;
    }

    @Bean
    CustomerRepository mockCustomerRepository() {
        CustomerRepository mockRepository = mock(CustomerRepository.class);
        when(mockRepository.findAll()).thenReturn(List.of(testCustomer(), testCustomer()));
        Customer customer = testCustomer();
        customer.setId(1);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(mockRepository.findById(1)).thenReturn(Optional.of(customer));
        when(mockRepository.save(any(Customer.class))).thenReturn(customer);
        return mockRepository;
    }

    @Bean
    CustomerService customerService() {
        return new CustomerServiceImpl(mockCustomerRepository());
    }
}
