package org.pizzacrud.controller.configuration;

import org.pizzacrud.controller.OrderController;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.dto.OrderDto;
import org.pizzacrud.mapper.CustomerMapper;
import org.pizzacrud.mapper.OrderMapper;
import org.pizzacrud.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class OrderControllerTestConfiguration {

    @Bean
    public Order testOrder() {
        Customer customer = new Customer();
        customer.setId(1);
        Order order = new Order();
        order.setCustomer(customer);
        order.setPizzas(List.of());
        return order;
    }

    @Bean
    public OrderDto testOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1);
        return orderDto;
    }

    @Bean
    public OrderService mockOrderService() {
        OrderService service = mock(OrderService.class);
        when(service.findById(anyInt())).thenReturn(testOrder());
        when(service.save(any())).thenReturn(testOrder());
        return service;
    }

    @Bean
    public OrderMapper mockCustomerMapper() {
        OrderMapper mapper = mock(OrderMapper.class);

        when(mapper.toDto(any())).thenReturn(testOrderDto());
        when(mapper.toEntity(any())).thenReturn(testOrder());
        when(mapper.toDtoList(any())).thenReturn(List.of(testOrderDto(), testOrderDto()));
        when(mapper.toEntityList(any())).thenReturn(List.of(testOrder(), testOrder()));

        return mapper;
    }

    @Bean
    public OrderController orderController() {
        return new OrderController(mockOrderService(), mockCustomerMapper());
    }
}
