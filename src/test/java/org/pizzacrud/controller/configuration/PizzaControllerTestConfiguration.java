package org.pizzacrud.controller.configuration;

import org.pizzacrud.controller.PizzaController;
import org.pizzacrud.database.entity.Pizza;
import org.pizzacrud.dto.IngredientDto;
import org.pizzacrud.dto.PizzaDto;
import org.pizzacrud.mapper.IngredientMapper;
import org.pizzacrud.mapper.IngredientMapperTest;
import org.pizzacrud.mapper.PizzaMapper;
import org.pizzacrud.service.PizzaService;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PizzaControllerTestConfiguration {

    @Bean
    public Pizza testPizza() {
        return new Pizza();
    }

    @Bean
    public PizzaDto testPizzaDto() {
        return new PizzaDto();
    }

    @Bean
    IngredientDto testIngredientDto() {
        return new IngredientDto(0, "test");
    }

    @Bean
    IngredientMapper mockIngredientMapper() {
        IngredientMapper mapper = mock(IngredientMapper.class);

        when(mapper.toDtoList(any())).thenReturn(List.of(testIngredientDto(), testIngredientDto()));

        return mapper;
    }

    @Bean
    PizzaMapper mockPizzaMapper() {
        PizzaMapper mapper = mock(PizzaMapper.class);

        when(mapper.toDto(any())).thenReturn(testPizzaDto());
        when(mapper.toEntity(any())).thenReturn(testPizza());
        when(mapper.toDtoList(any())).thenReturn(List.of(testPizzaDto()));
        when(mapper.toEntityList(any())).thenReturn(List.of(testPizza()));

        return mapper;
    }

    @Bean
    PizzaService mockPizzaService() {
        PizzaService service = mock(PizzaService.class);

        when(service.findById(anyInt())).thenReturn(testPizza());
        when(service.findAll()).thenReturn(List.of(testPizza(), testPizza()));
        when(service.create(any())).thenReturn(testPizza());
        when(service.update(anyInt(), any())).thenReturn(testPizza());

        return service;
    }

    @Bean
    PizzaController testPizzaController() {
        return new PizzaController(mockPizzaService(), mockPizzaMapper(), mockIngredientMapper());
    }
}
