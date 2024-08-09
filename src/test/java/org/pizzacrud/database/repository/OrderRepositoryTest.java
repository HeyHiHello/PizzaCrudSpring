package org.pizzacrud.database.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pizzacrud.configuration.MvcConfiguration;
import org.pizzacrud.database.TestDbInitializer;
import org.pizzacrud.database.entity.Customer;
import org.pizzacrud.database.entity.Order;
import org.pizzacrud.database.entity.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(MvcConfiguration.class)
@ContextConfiguration(classes = {DbTestConfiguration.class})
public class OrderRepositoryTest {
    @Autowired
    OrderRepository repository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PizzaRepository pizzaRepository;
    public static MySQLContainer mySQLContainer = TestDbInitializer.buildMySQLContainer();

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        TestDbInitializer.registerPgProperties(registry, mySQLContainer);
    }

    @BeforeAll
    static void setUp() {
        mySQLContainer.start();
    }

    @AfterAll
    static void tearDown() {
        mySQLContainer.stop();
    }

    @Test
    void findByIdTest() {
        assertEquals(1, repository.findById(1).get().getId());
    }

    @Test
    void saveTest() {
        Order order = new Order();
        Customer customer = customerRepository.findById(1).get();

        Pizza pizza = pizzaRepository.findById(1).get();
        Pizza pizza2 = pizzaRepository.findById(2).get();

        order.setId(0);
        order.setCustomer(customer);
        order.setPizzas(List.of(pizza, pizza2));

        Order savedOrder = repository.save(order);

        assertNotEquals(0, order.getId());
        assertNotEquals(0, savedOrder.getId());
        assertEquals(order.getId(), savedOrder.getId());
    }

    @Test
    void updateTest() {
        Order order = repository.findById(1).get();
        Customer customer = customerRepository.findById(2).get();
        order.setCustomer(customer);

        order.setPizzas(List.of(pizzaRepository.findById(2).get()));
        Order savedOrder = repository.save(order);
        assertNotEquals(0, order.getId());
        assertNotEquals(0, savedOrder.getId());
        assertEquals(order.getId(), savedOrder.getId());
        assertEquals(order.getCustomer().getId(), savedOrder.getCustomer().getId());
        assertEquals(customer.getId(), savedOrder.getCustomer().getId());    }

    @Test
    void deleteTest() {
        repository.deleteById(1);

        assertTrue(repository.findById(1).isEmpty());
    }
}
