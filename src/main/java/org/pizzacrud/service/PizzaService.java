package org.pizzacrud.service;

import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.entity.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> findAll();
    Pizza findById(int id);
    Pizza create(Pizza pizza);
    Pizza setIngredients(int pizzaId, List<Integer> ingredientIds);
    Pizza update(int id, Pizza pizza);
    void delete(int id);
}
