package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
    List<Ingredient> findAll();
}
