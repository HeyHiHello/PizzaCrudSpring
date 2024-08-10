package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.service.IngredientService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that serve crud requests on Ingredient entity
 */
@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    public IngredientController(IngredientService ingredientService, IngredientMapper ingredientMapper) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
    }

    /**
     * Get all Ingredients from DB
     * @return ResponseEntity with List of Ingredients and response code OK
     */
    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<Ingredient> allIngredients = ingredientService.findAll();
        List<IngredientDto> dtos = ingredientMapper.toDtoList(allIngredients);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Get Ingredient by its id
     * @param ingredientId id of the Ingredient
     * @return ResponseEntity with requested Ingredient and status code OK
     */
    @GetMapping("{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable(name = "id") int ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        IngredientDto dto = ingredientMapper.toDto(ingredient);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Create new Ingredient
     * @param ingredientDto Request body with ingredient data
     * @return ResponseEntity with created Ingredient and status code CREATED
     */
    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
        Ingredient createdIngredient = ingredientService.create(ingredient);

        IngredientDto createdIngredientDto = ingredientMapper.toDto(createdIngredient);
        return new ResponseEntity<>(createdIngredientDto, HttpStatus.CREATED);
    }

    /**
     * Update Ingredient by its id
     * @param ingredientId id of the Ingredient
     * @param ingredientDto request body with Ingredient data
     * @return ResponseEntity with saved ingredient
     */
    @PatchMapping("{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable(name = "id") int ingredientId, @RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
        Ingredient updatedIngredient = ingredientService.update(ingredientId, ingredient);
        IngredientDto updatedIngredientDto = ingredientMapper.toDto(updatedIngredient);
        return new ResponseEntity<>(updatedIngredientDto, HttpStatus.OK);
    }

    /**
     * Delete Ingredient by id
     * @param ingredientId id of the Ingredient
     * @return ResponseEntity with updated Ingredient
     */
    @DeleteMapping("{id}")
    public ResponseEntity<IngredientDto> deleteIngredient(@PathVariable(name = "id") int ingredientId) {
        ingredientService.delete(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Repository didn't find any Entity by given id
     * @param e Exception
     * @return ResponseEntity with status code NOT FOUND
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Insertion finished with error due to invalid request body
     * @param e Exception
     * @return ResponseEntity with response code BAD REQUEST
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Unknown server error
     * @param e exception
     * @return ResponseEntity with response code INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
