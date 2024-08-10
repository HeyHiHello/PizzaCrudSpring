package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.database.repository.IngredientRepository;
import org.pizzacrud.database.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaServiceImpl implements PizzaService {
    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;

    public PizzaServiceImpl(PizzaRepository pizzaRepository,  IngredientRepository ingredientRepository) {
        this.pizzaRepository = pizzaRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Find all Pizzas
     * @return List of Pizzas
     */
    @Override
    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    /**
     * Find Pizza by its id
     * @param id id of the Pizza
     * @return Found Pizza
     */
    @Override
    public Pizza findById(int id) {
        return pizzaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Create new Pizza
     * @param pizza Pizza data
     * @return Created Pizza
     */
    @Override
    public Pizza create(Pizza pizza) {
        pizza.setId(0);
        return pizzaRepository.save(pizza);
    }

    /**
     * Set Ingredients to Pizza
     * Old Ingredients will be replaced
     * @param pizzaId id of the Pizza
     * @param ingredientIds List of Ingredients id
     * @return Updated Pizza
     */
    @Override
    public Pizza setIngredients(int pizzaId, List<Integer> ingredientIds) {
        Pizza pizza = findById(pizzaId);
        List<Ingredient> ingredients = ingredientRepository.findByIdIn(ingredientIds);
        pizza.setIngredients(ingredients);
        pizzaRepository.save(pizza);
        return pizza;
    }

    /**
     * Update Pizza by its id
     * @param id id of the Pizza
     * @param pizza Pizza's data
     * @return Updated pizza
     */
    @Override
    public Pizza update(int id, Pizza pizza) {
         Pizza dbPizza = pizzaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
         dbPizza.setName(pizza.getName());
         dbPizza.setPrice(pizza.getPrice());
         return pizzaRepository.save(dbPizza);
    }

    /**
     * Delete Pizza by its id
     * @param id id of the Pizza
     */
    @Override
    public void delete(int id) {
        if (!pizzaRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        pizzaRepository.deleteById(id);
    }
}
