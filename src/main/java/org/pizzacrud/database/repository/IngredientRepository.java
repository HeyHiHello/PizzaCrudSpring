package org.pizzacrud.database.repository;

import org.pizzacrud.database.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}
