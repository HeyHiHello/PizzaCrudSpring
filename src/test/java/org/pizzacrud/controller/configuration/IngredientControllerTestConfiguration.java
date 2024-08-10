package org.pizzacrud.controller.configuration;

import org.pizzacrud.controller.IngredientController;
import org.pizzacrud.database.entity.Ingredient;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.mapper.IngredientMapperTest;
import org.pizzacrud.service.IngredientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class IngredientControllerTestConfiguration {

    @Bean
    @Scope("prototype")
    public Ingredient testIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1);
        ingredient.setName("test");
        return ingredient;
    }

    @Bean
    @Scope("prototype")
    public IngredientDto testIngredientDto() {
        return new IngredientDto(1, "test");
    }

    @Bean
    public IngredientService mockIngredientService() {
        IngredientService service = mock(IngredientService.class);

        when(service.findAll()).thenReturn(List.of(testIngredient(), testIngredient()));
        when(service.findById(anyInt())).thenReturn(testIngredient());
        when(service.create(any())).thenReturn(testIngredient());
        when(service.update(anyInt(), any())).thenReturn(testIngredient());

        return service;
    }

    @Bean
    public IngredientMapper mockIngredientMapper() {
        IngredientMapper mapper = mock(IngredientMapper.class);

        when(mapper.toDto(any())).thenReturn(testIngredientDto());
        when(mapper.toEntity(any())).thenReturn(testIngredient());
        when(mapper.toDtoList(any())).thenReturn(List.of(testIngredientDto(), testIngredientDto()));
        when(mapper.toEntityList(any())).thenReturn(List.of(testIngredient(), testIngredient()));

        return mapper;
    }

    @Bean
    public IngredientController ingredientController() {
        return new IngredientController(mockIngredientService(), mockIngredientMapper());
    }
}
