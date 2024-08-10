package org.pizzacrud.service.configuration;

import jakarta.persistence.EntityNotFoundException;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.database.repository.IngredientRepository;
import org.pizzacrud.service.IngredientService;
import org.pizzacrud.service.IngredientServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@Configuration
public class IngredientServiceTestConfiguration {
    @Bean
    @Scope("prototype")
    public Ingredient testIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("testIngredient");
        return ingredient;
    }

    @Bean
    public IngredientRepository mockIngredientRepository() {
        IngredientRepository mockIngredientRepository = mock(IngredientRepository.class);

        Ingredient ingredientWithId = testIngredient();
        ingredientWithId.setId(1);
        when(mockIngredientRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(mockIngredientRepository.findById(1)).thenReturn(Optional.of(ingredientWithId));
        when(mockIngredientRepository.findAll()).thenReturn(List.of(testIngredient(), testIngredient()));
        when(mockIngredientRepository.findByIdIn(any())).thenReturn(List.of(testIngredient(), testIngredient()));
        when(mockIngredientRepository.save(any())).thenReturn(ingredientWithId);
        doReturn(false).when(mockIngredientRepository).existsById(anyInt());
        doReturn(true).when(mockIngredientRepository).existsById(1);
        doNothing().when(mockIngredientRepository).deleteById(anyInt());
        doReturn(List.of(testIngredient())).when(mockIngredientRepository).findByIdIn(List.of(1));
        return mockIngredientRepository;
    }

    @Bean
    public IngredientService ingredientService() {
        return new IngredientServiceImpl(mockIngredientRepository());
    }
}
