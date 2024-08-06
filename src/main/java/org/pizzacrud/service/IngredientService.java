package org.pizzacrud.service;

import org.pizzacrud.database.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll();

    Ingredient findById(int id);

    Ingredient create(Ingredient ingredient);

    Ingredient update(int id, Ingredient ingredient);

    void delete(int id);
}
