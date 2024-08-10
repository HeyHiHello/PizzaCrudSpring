package org.pizzacrud.database.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.TestDbInitializer;
import org.pizzacrud.database.entity.Pizza;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MySQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(DbTestConfiguration.class)
public class PizzaRepositoryTest {
    @Autowired
    PizzaRepository repository;
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
        List<Pizza> ingredient = repository.findAll();
        assertTrue(ingredient.size() >= 2);
    }

    @Test
    void findByIdTest() {
        assertEquals(1, repository.findById(1).get().getId());
    }

    @Test
    void saveTest() {
        Pizza pizza = new Pizza();
        pizza.setName("Pizza");
        Pizza savedPizza = repository.save(pizza);
        assertNotEquals(0, pizza.getId());
        assertNotEquals(0, pizza.getId());
        assertEquals(pizza.getName(), savedPizza.getName());
    }

    @Test
    void updateTest() {
        Pizza pizza = repository.findById(1).get();
        pizza.setName("updatedPizza");
        Pizza savedPizza = repository.save(pizza);
        assertEquals("updatedPizza", pizza.getName());
        assertEquals(pizza.getId(), savedPizza.getId());
        assertEquals(pizza.getName(), savedPizza.getName());
    }

    @Test
    void deleteTest() {
        repository.deleteById(1);

        assertTrue(repository.findById(1).isEmpty());
    }
}
