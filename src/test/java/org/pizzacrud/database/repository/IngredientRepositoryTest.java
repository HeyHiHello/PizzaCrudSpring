package org.pizzacrud.database.repository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.pizzacrud.configuration.MvcConfiguration;
import org.pizzacrud.database.TestDbInitializer;
import org.pizzacrud.database.entity.Ingredient;
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
class IngredientRepositoryTest {

    @Autowired
    IngredientRepository repository;
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
        List<Ingredient> ingredient = repository.findAll();
        assertTrue(ingredient.size() >= 6);
    }

    @Test
    void findByIdTest() {
        assertEquals(1, repository.findById(1).get().getId());
    }

    @Test
    void findByIdInTest() {
        List<Integer> ids = List.of(1, 2, 3);

        List<Ingredient> ingredients = repository.findByIdIn(ids);

        assertEquals(3, ingredients.size());
        List<Integer> idsFromDb = ingredients.stream().map(Ingredient::getId).toList();
        assertEquals(ids, idsFromDb);
    }

    @Test
    void saveTest() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(0);
        ingredient.setName("test");
        Ingredient savedIngredient = repository.save(ingredient);

        assertNotEquals(0, savedIngredient.getId());
        assertEquals(ingredient.getId(), savedIngredient.getId());
        assertEquals(ingredient.getName(), savedIngredient.getName());
    }

    @Test
    void updateTest() {
        Ingredient ingredient = repository.findById(1).get();
        ingredient.setName("test2");

        repository.save(ingredient);
        Ingredient ingredient2 = repository.findById(1).get();
        assertEquals("test2", ingredient2.getName());
    }

    @Test
    void deleteTest() {
        repository.deleteById(1);

        assertTrue(repository.findById(1).isEmpty());
    }
}
