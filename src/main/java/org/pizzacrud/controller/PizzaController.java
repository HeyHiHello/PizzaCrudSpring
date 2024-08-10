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

/**
 * Controller that serve crud requests on Order entity
 */
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

    /**
     * Get all Pizza from DB
     * @return ResponseEntity with pizzas and response code OK
     */
    @GetMapping
    public ResponseEntity<List<PizzaDto>> getAllPizzas() {
        List<Pizza> allPizzas = pizzaService.findAll();
        List<PizzaDto> dtos = pizzaMapper.toDtoList(allPizzas);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Get Pizza by id
     * @param id id of the Pizza
     * @return ResponseEntity with created Pizza and response code OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<PizzaDto> getPizzaById(@PathVariable("id") int id) {
        Pizza pizza = pizzaService.findById(id);
        PizzaDto dto = pizzaMapper.toDto(pizza);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Get Ingredients of a Pizza by its id
     * @param id id of the Pizza
     * @return ResponseEntity with list of Ingredients and response code OK
     */
    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<IngredientDto>> getPizzaIngredients(@PathVariable("id") int id) {
        Pizza pizza = pizzaService.findById(id);
        List<Ingredient> ingredients = pizza.getIngredients();
        List<IngredientDto> dtos = ingredientMapper.toDtoList(ingredients);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Create new Pizza
     * @param pizzaDto Request body with Pizza data
     * @return ResponseEntity with created Pizza and response code CREATED
     */
    @PostMapping
    public ResponseEntity<PizzaDto> createPizza(@RequestBody PizzaDto pizzaDto) {
        Pizza pizza = pizzaMapper.toEntity(pizzaDto);
        Pizza createdPizza = pizzaService.create(pizza);
        PizzaDto createdPizzaDto = pizzaMapper.toDto(createdPizza);
        return new ResponseEntity<>(createdPizzaDto, HttpStatus.CREATED);
    }

    /**
     * Set Ingredients to Pizza by its id
     * @param id id of the Pizza
     * @param ingredientIds List of Ingredients
     * @return ResponseEntity with changed Pizza and response code OK
     */
    @PostMapping("/{id}/ingredients")
    public ResponseEntity<PizzaDto> addIngredientsToPizza(@PathVariable("id") int id, @RequestBody List<Integer> ingredientIds) {
        Pizza updatedPizza = pizzaService.setIngredients(id, ingredientIds);
        PizzaDto pizzaDto = pizzaMapper.toDto(updatedPizza);
        return new ResponseEntity<>(pizzaDto, HttpStatus.OK);
    }

    /**
     * 
     * @param id
     * @param pizzaDto
     * @return
     */
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
