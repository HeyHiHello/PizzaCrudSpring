package org.pizzacrud.service.configuration;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.database.repository.OrderRepository;
import org.pizzacrud.service.OrderService;
import org.pizzacrud.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@Configuration
@Import({CustomerServiceTestConfiguration.class, PizzaServiceTestConfiguration.class})
public class OrderServiceTestConfiguration {
    @Autowired
    CustomerServiceTestConfiguration customerConfiguration;
    @Autowired
    PizzaServiceTestConfiguration pizzaConfiguration;

    @Bean
    @Scope("prototype")
    public Order testOrder() {
        Order order = new Order();
        Customer customer = customerConfiguration.testCustomer();
        List<Pizza> pizzas = List.of(pizzaConfiguration.testPizza(), pizzaConfiguration.testPizza());
        order.setCustomer(customer);
        order.setPizzas(pizzas);
        return order;
    }

    @Bean
    public OrderRepository mockOrderRepository() {
        OrderRepository orderRepository = mock(OrderRepository.class);
        doReturn(Optional.empty()).when(orderRepository).findById(anyInt());
        Order orderWithId = testOrder();
        orderWithId.setId(1);
        doReturn(Optional.of(orderWithId)).when(orderRepository).findById(1);
        Order savedOrder = testOrder();
        savedOrder.setId(1);
        doReturn(savedOrder).when(orderRepository).save(any());
        doThrow(EntityNotFoundException.class).when(orderRepository).deleteById(anyInt());
        doNothing().when(orderRepository).deleteById(1);
        return orderRepository;
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(mockOrderRepository());
    }
}
