package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.controller.configuration.PizzaControllerTestConfiguration;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.dto.PizzaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(PizzaControllerTestConfiguration.class)
class PizzaControllerTest {
    @Autowired
    private PizzaDto testPizzaDto;
    @Autowired
    private PizzaController pizzaController;

    @Test
    void getAllTest() {
        ResponseEntity<List<PizzaDto>> responseEntity = pizzaController.getAllPizzas();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getByIdTest() {
        ResponseEntity<PizzaDto> responseEntity = pizzaController.getPizzaById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void getPizzaIngredients() {
        ResponseEntity<List<IngredientDto>> responseEntity = pizzaController.getPizzaIngredients(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createPizzaTest() {
        ResponseEntity<PizzaDto> responseEntity = pizzaController.createPizza(testPizzaDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void updatePizzaTest() {
        ResponseEntity<PizzaDto> responseEntity = pizzaController.updatePizza(1, testPizzaDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deletePizzaTest() {
        ResponseEntity<PizzaDto> responseEntity = pizzaController.deletePizza(1);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void addIngredientsTest() {
        ResponseEntity<PizzaDto> responseEntity = pizzaController.addIngredientsToPizza(1,  List.of());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void entityNotFoundExceptionTest() {
        ResponseEntity<Object> responseEntity = pizzaController.handleEntityNotFoundException(new EntityNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<Object> responseEntity = pizzaController.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void exception() {
        ResponseEntity<Object> responseEntity = pizzaController.handleAllException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
