package org.pizzacrud.converter.configuration;

import org.pizzacrud.converter.IngredientDtoByIdConverter;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.mapper.IngredientMapperTest;
import org.pizzacrud.service.IngredientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class IngredientConverterTestConfiguration {
    @Bean
    @Scope("prototype")
    public Ingredient testIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("testIngredient");
        return ingredient;
    }

    @Bean
    @Scope("prototype")
    public IngredientDto testIngredientDto() {
        return new IngredientDto(1, "testIngredient");
    }

    @Bean
    IngredientService mockIngredientService() {
        IngredientService ingredientService = mock(IngredientService.class);
        when(ingredientService.findById(anyInt())).thenReturn(testIngredient());
        return ingredientService;
    }

    @Bean
    IngredientMapper mockIngredientMapper() {
        IngredientMapper ingredientMapper = mock(IngredientMapper.class);
        when(ingredientMapper.toDto(any())).thenReturn(testIngredientDto());
        return ingredientMapper;
    }

    @Bean
    IngredientDtoByIdConverter converter() {
        return new IngredientDtoByIdConverter(mockIngredientService(), mockIngredientMapper());
    }
}
