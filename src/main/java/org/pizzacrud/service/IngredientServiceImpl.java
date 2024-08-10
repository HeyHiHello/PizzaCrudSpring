package org.pizzacrud.service;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Find all Ingredients
     * @return List of all Ingredients
     */
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    /**
     * Find ingredient by its id
     * @param id id of the Ingredient
     * @return Ingredient
     */
    public Ingredient findById(int id) {
        return ingredientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Create new Ingredient
     * @param ingredient Ingredient data
     * @return Created Ingredient
     */
    public Ingredient create(Ingredient ingredient) {
        ingredient.setId(0);
        return ingredientRepository.save(ingredient);
    }

    /**
     * Update existing Ingredient by its id
     * @param id id of the Ingredient
     * @param ingredient Ingredient data
     * @return Created Ingredient
     */
    public Ingredient update(int id, Ingredient ingredient) {
        ingredient.setId(id);
        return ingredientRepository.save(ingredient);
    }

    /**
     * Delete Ingredient by its id
     * @param id id of the Ingredient
     */
    public void delete(int id) {
        if (!ingredientRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        ingredientRepository.deleteById(id);
    }
}
