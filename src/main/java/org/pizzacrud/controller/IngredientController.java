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

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    public IngredientController(IngredientService ingredientService, IngredientMapper ingredientMapper) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<Ingredient> allIngredients = ingredientService.findAll();
        List<IngredientDto> dtos = ingredientMapper.toDtoList(allIngredients);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable(name = "id") int ingredientId) {
        Ingredient ingredient = ingredientService.findById(ingredientId);
        IngredientDto dto = ingredientMapper.toDto(ingredient);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
        Ingredient createdIngredient = ingredientService.create(ingredient);

        IngredientDto createdIngredientDto = ingredientMapper.toDto(createdIngredient);
        return new ResponseEntity<>(createdIngredientDto, HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable(name = "id") int ingredientId, @RequestBody IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientMapper.toEntity(ingredientDto);
        Ingredient updatedIngredient = ingredientService.update(ingredientId, ingredient);
        IngredientDto updatedIngredientDto = ingredientMapper.toDto(updatedIngredient);
        return new ResponseEntity<>(updatedIngredientDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<IngredientDto> deleteIngredient(@PathVariable(name = "id") int ingredientId) {
        ingredientService.delete(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
