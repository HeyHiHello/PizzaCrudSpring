package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.service.configuration.IngredientServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(IngredientServiceTestConfiguration.class)
class IngredientServiceTest {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private Ingredient testIngredient;

    @Test
    void getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.findAll();
        assertEquals(2, ingredients.size());
    }

    @Test
    void getIngredientById() {
        Ingredient ingredient = ingredientService.findById(1);
        assertEquals(1, ingredient.getId());
        assertEquals(testIngredient.getName(), ingredient.getName());
    }

    @Test
    void getIngredientByIdExceptionTest() {
        int invalidId = 1000;
        assertThrows(EntityNotFoundException.class, () ->ingredientService.findById(invalidId));
    }

    @Test
    void createIngredientTest() {
        Ingredient ingredient = ingredientService.create(testIngredient);
        assertEquals(testIngredient.getName(), ingredient.getName());
    }

    @Test
    void updateIngredientTest() {
        Ingredient ingredient = ingredientService.update(1, testIngredient);
        assertEquals(testIngredient.getName(), ingredient.getName());
    }

    @Test
    void deleteIngredientTest() {
        assertDoesNotThrow(() -> ingredientService.delete(1));
    }

    @Test
    void deleteIngredientExceptionTest() {
        int invalidId = 1000;
        assertThrows(EntityNotFoundException.class, () -> ingredientService.delete(invalidId));
    }
}
