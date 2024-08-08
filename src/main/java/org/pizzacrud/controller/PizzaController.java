package org.pizzacrud.controller;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.dto.PizzaDto;
import org.pizzacrud.mapper.IngredientListMapper;
import org.pizzacrud.mapper.PizzaListMapper;
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
    private final PizzaListMapper pizzaListMapper;
    private final IngredientListMapper ingredientListMapper;

    public PizzaController(PizzaService pizzaService, PizzaMapper pizzaMapper, PizzaListMapper pizzaListMapper, IngredientListMapper ingredientListMapper) {
        this.pizzaService = pizzaService;
        this.pizzaMapper = pizzaMapper;
        this.pizzaListMapper = pizzaListMapper;
        this.ingredientListMapper = ingredientListMapper;
    }

    @GetMapping
    public List<PizzaDto> getAllPizzas() {
        List<Pizza> allPizzas = pizzaService.findAll();
        return pizzaListMapper.toDtoList(allPizzas);
    }

    @GetMapping("/{id}")
    public PizzaDto getPizzaById(@PathVariable("id") int id) {
        return pizzaMapper.toDto(pizzaService.findById(id));
    }

    @GetMapping("/{id}/ingredients")
    public List<IngredientDto> getPizzaIngredients(@PathVariable("id") int id) {
        Pizza pizza = pizzaService.findById(id);
        return ingredientListMapper.toDtoList(pizza.getIngredients());
    }

    @PostMapping
    public PizzaDto createPizza(@RequestBody PizzaDto pizzaDto) {
        Pizza pizza = pizzaMapper.toEntity(pizzaDto);
        Pizza createdPizza = pizzaService.create(pizza);
        return pizzaMapper.toDto(createdPizza);
    }

    @PostMapping("/{id}/ingredients")
    public PizzaDto addIngredientToPizza(@PathVariable("id") int id, @RequestBody List<Integer> ingredientIds) {
        Pizza updatedPizza = pizzaService.setIngredients(id, ingredientIds);
        return pizzaMapper.toDto(updatedPizza);
    }

    @PatchMapping("/{id}")
    public PizzaDto updatePizza(@PathVariable("id") int id, @RequestBody PizzaDto pizzaDto) {
        Pizza pizza = pizzaMapper.toEntity(pizzaDto);
        Pizza updatedPizza = pizzaService.update(id, pizza);
        return pizzaMapper.toDto(updatedPizza);
    }

    @DeleteMapping("/{id}")
    public void deletePizza(@PathVariable("id") int id) {
        pizzaService.delete(id);
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
