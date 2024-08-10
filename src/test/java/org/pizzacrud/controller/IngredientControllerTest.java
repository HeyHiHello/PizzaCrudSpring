package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.pizzacrud.controller.configuration.IngredientControllerTestConfiguration;
import org.pizzacrud.dto.IngredientDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(IngredientControllerTestConfiguration.class)
class IngredientControllerTest {
    @Autowired
    private IngredientController ingredientController;
    @Autowired
    private IngredientDto testIngredientDto;


    @Test
    void findAllTest() {
        ResponseEntity<List<IngredientDto>> responseEntity = ingredientController.getAllIngredients();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void findByIdTest() {
        ResponseEntity<IngredientDto> responseEntity = ingredientController.getIngredientById(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void createTest() {
        ResponseEntity<IngredientDto> responseEntity = ingredientController.createIngredient(testIngredientDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void updateTest() {
        ResponseEntity<IngredientDto> responseEntity = ingredientController.updateIngredient(1, testIngredientDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void deleteTest() {
        ResponseEntity<IngredientDto> responseEntity = ingredientController.deleteIngredient(1);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    void entityNotFoundExceptionTest() {
        ResponseEntity<Object> responseEntity = ingredientController.handleEntityNotFoundException(new EntityNotFoundException());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<Object> responseEntity = ingredientController.handleDataIntegrityViolationException(new DataIntegrityViolationException(""));
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void exception() {
        ResponseEntity<Object> responseEntity = ingredientController.handleAllException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
