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

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient findById(int id) {
        return ingredientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Ingredient create(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(int id, Ingredient ingredient) {
        ingredient.setId(id);
        return ingredientRepository.save(ingredient);
    }

    public void delete(int id) {
        ingredientRepository.deleteById(id);
    }
}
