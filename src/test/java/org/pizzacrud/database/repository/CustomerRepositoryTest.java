package org.pizzacrud.database.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pizzacrud.configuration.MvcConfiguration;
import org.pizzacrud.database.TestDbInitializer;
import org.pizzacrud.database.entity.Address;
import org.pizzacrud.database.entity.Customer;
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
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;
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
    void findAllTest() {
        List<Customer> ingredient = repository.findAll();
        assertTrue(ingredient.size() >= 2);
    }

    @Test
    void findByIdTest() {
        assertEquals(1, repository.findById(1).get().getId());
    }

    @Test
    void saveTest() {
        Customer customer = new Customer();
        customer.setId(0);
        customer.setFirstname("test");
        customer.setLastname("test");
        Address address = new Address();
        address.setStreet("street");
        address.setCity("city");
        address.setBuilding("building");
        address.setId(0);
        address.setCustomer(customer);
        customer.setAddress(address);

        Customer savedCustomer = repository.save(customer);

        assertNotEquals(0, customer.getId());
        assertNotEquals(0, customer.getAddress().getId());

        assertEquals(customer.getFirstname(), savedCustomer.getFirstname());
        assertEquals(customer.getLastname(), savedCustomer.getLastname());
        assertEquals(customer.getAddress().getStreet(), savedCustomer.getAddress().getStreet());
    }

    @Test
    void updateTest() {
        Customer customer = repository.findById(1).get();
        customer.setFirstname("test");
        customer.setLastname("test");

        Customer savedCustomer = repository.save(customer);

        assertEquals(customer.getFirstname(), savedCustomer.getFirstname());
        assertEquals(customer.getLastname(), savedCustomer.getLastname());
        assertEquals(customer.getAddress().getStreet(), savedCustomer.getAddress().getStreet());
    }

    @Test
    void deleteTest() {
        repository.deleteById(1);

        assertTrue(repository.findById(1).isEmpty());
    }
}
