package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.service.configuration.PizzaServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(PizzaServiceTestConfiguration.class)
class PizzaServiceTest {
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private Pizza testPizza;
    @Autowired
    private Ingredient ingredient;

    @Test
    void findAllTest() {
        List<Pizza> pizzaList = pizzaService.findAll();
        assertEquals(2, pizzaList.size());
    }

    @Test
    void findByIdTest() {
        Pizza pizza = pizzaService.findById(1);
        assertEquals(1, pizza.getId());
    }

    @Test
    void createTest() {
        Pizza pizza = pizzaService.create(testPizza);
        assertEquals(1, pizza.getId());
        assertEquals(testPizza.getName(), pizza.getName());
    }

    @Test
    void updateTest() {
        Pizza pizza = pizzaService.update(1, testPizza);
        assertEquals(1, pizza.getId());
        assertEquals(testPizza.getName(), pizza.getName());
    }

    @Test
    void setIngredientsTest() {
        List<Integer> ingredients = List.of(1, 1);
        assertDoesNotThrow(() -> pizzaService.setIngredients(1, ingredients));
    }

    @Test
    void deleteTest() {
        assertDoesNotThrow(() -> pizzaService.delete(1));
    }

    @Test
    void deleteExceptionTest() {
        int invalidId = 1000;
        assertThrows(EntityNotFoundException.class, () -> pizzaService.delete(invalidId));
    }
}
