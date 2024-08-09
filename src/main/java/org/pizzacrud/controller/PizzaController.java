package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.dto.PizzaDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.mapper.PizzaMapper;
import org.pizzacrud.service.PizzaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizza")
public class PizzaController {
    private final PizzaService pizzaService;
    private final PizzaMapper pizzaMapper;
    private final IngredientMapper ingredientMapper;

    public PizzaController(PizzaService pizzaService, PizzaMapper pizzaMapper, IngredientMapper ingredientMapper) {
        this.pizzaService = pizzaService;
        this.pizzaMapper = pizzaMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping
    public ResponseEntity<List<PizzaDto>> getAllPizzas() {
        List<Pizza> allPizzas = pizzaService.findAll();
        List<PizzaDto> dtos = pizzaMapper.toDtoList(allPizzas);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable("id") int id) {
        Pizza pizza = pizzaService.findById(id);
        PizzaDto dto = pizzaMapper.toDto(pizza);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<IngredientDto>> getPizzaIngredients(@PathVariable("id") int id) {
        Pizza pizza = pizzaService.findById(id);
        List<Ingredient> ingredients = pizza.getIngredients();
        List<IngredientDto> dtos = ingredientMapper.toDtoList(ingredients);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PizzaDto> createPizza(@RequestBody PizzaDto pizzaDto) {
        Pizza pizza = pizzaMapper.toEntity(pizzaDto);
        Pizza createdPizza = pizzaService.create(pizza);
        PizzaDto createdPizzaDto = pizzaMapper.toDto(createdPizza);
        return new ResponseEntity<>(createdPizzaDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/ingredients")
    public ResponseEntity<PizzaDto> addIngredientsToPizza(@PathVariable("id") int id, @RequestBody List<Integer> ingredientIds) {
        Pizza updatedPizza = pizzaService.setIngredients(id, ingredientIds);
        PizzaDto pizzaDto = pizzaMapper.toDto(updatedPizza);
        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PizzaDto> updatePizza(@PathVariable("id") int id, @RequestBody PizzaDto pizzaDto) {
        Pizza pizza = pizzaMapper.toEntity(pizzaDto);
        Pizza updatedPizza = pizzaService.update(id, pizza);
        PizzaDto dto = pizzaMapper.toDto(updatedPizza);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PizzaDto> deletePizza(@PathVariable("id") int id) {
        pizzaService.delete(id);
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
