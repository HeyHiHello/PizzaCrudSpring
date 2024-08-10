package org.pizzacrud.converter.configuration;

import org.pizzacrud.converter.CustomerDtoByIdConverter;
import org.pizzacrud.database.entity.Address;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.dto.AddressDto;
import org.pizzacrud.dto.CustomerDto;
import org.pizzacrud.mapper.CustomerMapper;
import org.pizzacrud.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class CustomerConverterTestConfiguration {

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
    AddressDto testAddressDto() {
        return new AddressDto("testStreet", "testCity", "testBuilding");
    }

    @Bean
    @Scope("prototype")
    Customer testCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstname("testFirstname");
        customer.setLastname("testLastname");
        customer.setAddress(testAddress());
        return customer;
    }

    @Bean
    @Scope("prototype")
    CustomerDto testCustomerDto() {
        return new CustomerDto(1, "testFirstname", "testLastname", testAddressDto());
    }

    @Bean
    public CustomerService mockCustomerService() {
        CustomerService customerService = mock(CustomerService.class);
        when(customerService.findById(anyInt())).thenReturn(testCustomer());
        return customerService;
    }

    @Bean
    public CustomerMapper mockCustomerMapper() {
        CustomerMapper customerMapper = mock(CustomerMapper.class);
        when(customerMapper.toDto(any())).thenReturn(testCustomerDto());
        return customerMapper;
    }

    @Bean
    public CustomerDtoByIdConverter customerDtoByIdConverter() {
        return new CustomerDtoByIdConverter(mockCustomerService(), mockCustomerMapper());
    }
}
