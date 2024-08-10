package org.pizzacrud.controller.configuration;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.controller.CustomerController;
import org.pizzacrud.database.entity.Address;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.dto.AddressDto;
import org.pizzacrud.dto.CustomerDto;
import org.pizzacrud.dto.OrderDto;
import org.pizzacrud.mapper.CustomerMapper;
import org.pizzacrud.mapper.OrderMapper;
import org.pizzacrud.service.CustomerService;
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
public class CustomerControllerTestConfiguration {
    @Bean
    @Scope("prototype")
    Address testAddress() {
        Address address = new Address();
        address.setId(0);
        address.setStreet("testStreet");
        address.setCity("testCity");
        address.setBuilding("testBuilding");
        return address;
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
    AddressDto testAddressDto() {
        return new AddressDto("testStreet", "testCity", "testBuilding");
    }

    @Bean
    @Scope("prototype")
    CustomerDto testCustomerDto() {
        return new CustomerDto(1, "testFirstname", "testLastname", testAddressDto());
    }

    @Bean
    CustomerService mockCustomerService() {
        CustomerService customerService = mock(CustomerService.class);

        when(customerService.findById(1)).thenReturn(testCustomer());
        when(customerService.findAll()).thenReturn(List.of(testCustomer(), testCustomer()));
        when(customerService.create(any())).thenReturn(testCustomer());
        when(customerService.update(anyInt(), any())).thenReturn(testCustomer());

        return customerService;
    }

    @Bean
    CustomerMapper mockCustomerMapper() {
        CustomerMapper customerMapper = mock(CustomerMapper.class);

        when(customerMapper.toDto(any())).thenReturn(testCustomerDto());
        when(customerMapper.toEntity(any())).thenReturn(testCustomer());
        when(customerMapper.toDtoList(any())).thenReturn(List.of(testCustomerDto(), testCustomerDto()));
        when(customerMapper.toEntityList(any())).thenReturn(List.of(testCustomer(), testCustomer()));

        return customerMapper;
    }

    @Bean
    @Scope("prototype")
    OrderDto testOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setCustomer(testCustomerDto());
        orderDto.setPizzas(List.of());
        return orderDto;
    }

    @Bean
    OrderMapper mockOrderMapper() {
        OrderMapper orderMapper = mock(OrderMapper.class);

        when(orderMapper.toDtoList(any())).thenReturn(List.of(testOrderDto(), testOrderDto()));

        return orderMapper;
    }

    @Bean
    CustomerController customerController() {
        return new CustomerController(mockCustomerService(), mockCustomerMapper(), mockOrderMapper());
    }

}
