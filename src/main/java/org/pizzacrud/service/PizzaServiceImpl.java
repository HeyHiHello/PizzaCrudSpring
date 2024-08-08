package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.database.repository.IngredientRepository;
import org.pizzacrud.database.repository.PizzaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository,  IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    @Override
    public Pizza findById(int id) {
        return pizzaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Pizza create(Pizza pizza) {
        pizza.setId(0);
        return pizzaRepository.save(pizza);
    }

    @Override
    public Pizza setIngredients(int pizzaId, List<Integer> ingredientIds) {
        Pizza pizza = findById(pizzaId);
        List<Ingredient> ingredients = ingredientRepository.findByIdIn(ingredientIds);
        pizza.setIngredients(ingredients);
        pizzaRepository.save(pizza);
        return pizza;
    }

    @Override
    public Pizza update(int id, Pizza newPizza) {
         Pizza pizza = pizzaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
         pizza.setName(newPizza.getName());
         pizza.setPrice(newPizza.getPrice());
         return pizzaRepository.save(pizza);
    }

    @Override
    public void delete(int id) {
        if (!pizzaRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        pizzaRepository.deleteById(id);
    }
}
